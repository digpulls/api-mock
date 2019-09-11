package testdata.employeetests;


public class EmployeeTestData {

    private String testDescription;
    private CreateEmployeeRequest request;
    private Integer expectedStatusCode;
    private String errorCode;

    public CreateEmployeeRequest getRequest() {
        return request;
    }

    public Integer getExpectedStatusCode() {
        return expectedStatusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getTestDescription() {
        return testDescription;
    }

    @Override
    public String toString() {
        return testDescription;
    }
}
