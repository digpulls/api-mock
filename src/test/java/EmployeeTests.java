import constants.BasePath;
import extentions.BaseTest;
import extentions.CallContext;
import extentions.JsonSource;
import helpers.EmployeeTestHelper;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testdata.employeetests.CreateEmployeeRequest;
import testdata.employeetests.CreateEmployeeResponse;
import testdata.employeetests.EmployeeTestData;
import testdata.employeetests.GetEmployeeResponse;
import testdata.employeetests.SuccessResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class EmployeeTests extends BaseTest {

    private Logger log = LogManager.getLogger(EmployeeTests.class);
    private CallContext context;
    private EmployeeTestHelper employeeTestHelper;

    @BeforeClass
    void login() {
        context = new CallContext(BasePath.DUMMY_REST_HOST);
        employeeTestHelper = new EmployeeTestHelper();
    }

    // Only writing tests for Create and Get methods for now
    @Test
    public void canCreateAndGetEmployee() throws Exception {
        log.info("Creating an Employee");
        String name = RandomStringUtils.randomAlphabetic(20);
        String salary = RandomStringUtils.randomNumeric(6);
        String age = RandomStringUtils.randomNumeric(2);

        CreateEmployeeResponse createdEmployee = employeeTestHelper.createEmployee(context,
                new CreateEmployeeRequest()
                        .setName(name)
                        .setSalary(Integer.valueOf(salary))
                        .setAge(Integer.valueOf(age)));
        try {

            assertThat("Employee ID not found", createdEmployee.getId(), not(isEmptyOrNullString()));
            assertThat("Employee Name mismatch!", createdEmployee.getName(), is(equalTo(name)));
            assertThat("Employee Age mismatch!", createdEmployee.getAge(), is(equalTo(Integer.valueOf(age))));
            assertThat("Employee Salary mismatch!", createdEmployee.getSalary(), is(equalTo(Integer.valueOf(salary))));

            log.info("Now retrieving the created Employee");

            GetEmployeeResponse retrievedEmployee = employeeTestHelper.getEmployee(context, createdEmployee.getId());
            assertThat("Employee ID mismatch!", retrievedEmployee.getId(), is(equalTo(createdEmployee.getId())));
            assertThat("Employee Name mismatch!", retrievedEmployee.getEmployeeName(), is(name));
            assertThat("Employee Age mismatch!", retrievedEmployee.getEmployeeAge(), is(equalTo(age)));
            assertThat("Employee Salary mismatch!", retrievedEmployee.getEmployeeSalary(), is(equalTo(salary)));

        } finally {
            cleanupEmployee(createdEmployee.getId());
        }
    }

    @JsonSource(resource = "data/invalid_employee_test_data.json", type = EmployeeTestData.class)
    @Test(dataProvider = "getData")
    public void cannotCreateEmployeeWithInvalidInput(EmployeeTestData testData) throws Exception {
        log.info("Creating an Employee with invalid input");
        Response response = employeeTestHelper
                .createEmployee(context, testData.getRequest(), testData.getExpectedStatusCode());

        response.then().assertThat()
                .body(containsString("error"))
                .body(containsString(testData.getErrorCode()));
    }

    private void cleanupEmployee(String id) throws Exception {
        SuccessResponse response = employeeTestHelper.deleteEmployee(context, id);
        assertThat(response.getSuccess().getText(), is(equalTo("successfully! deleted Records")));
    }
}
