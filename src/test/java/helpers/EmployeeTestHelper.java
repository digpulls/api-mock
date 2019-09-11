package helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import constants.ServicePath;
import extentions.CallContext;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import testdata.employeetests.CreateEmployeeRequest;
import testdata.employeetests.CreateEmployeeResponse;
import testdata.employeetests.GetEmployeeResponse;
import testdata.employeetests.SuccessResponse;

import java.io.IOException;
import java.util.List;

public class EmployeeTestHelper {

    public static String asString(Response response) {
        return response.getBody().htmlPath().get("html.body");
    }

    public CreateEmployeeResponse createEmployee(CallContext context, CreateEmployeeRequest request) throws Exception {
        Response response = createEmployee(context, request, HttpStatus.SC_OK);
        return SerializationHelper.deserialize(asString(response), CreateEmployeeResponse.class);
    }

    /*
        Use to get raw response
    */
    public Response createEmployee(CallContext context, CreateEmployeeRequest request, int statusCode) throws Exception {
        return RequestHelper.post(context, ServicePath.CREATE_EMPLOYEE_URI, request, statusCode);
    }

    public GetEmployeeResponse getEmployee(CallContext call, String employeeId) throws IOException {
        Response response = getEmployee(call, employeeId, HttpStatus.SC_OK);
        return SerializationHelper.deserialize(asString(response), GetEmployeeResponse.class);
    }

    public Response getEmployee(CallContext call, String employeeId, int statusCode) {
        return RequestHelper.get(call, String.format(ServicePath.GET_EMPLOYEE_URI, employeeId), statusCode);
    }

    public List<GetEmployeeResponse> getAllEmployees(CallContext call) throws IOException {
        Response response = RequestHelper.get(call, ServicePath.GET_EMPLOYEE_ALL_URI, HttpStatus.SC_OK);
        return SerializationHelper
                .deserialize(asString(response), new TypeReference<List<GetEmployeeResponse>>() {

                });
    }

    public void updateEmployee() {
        ///
    }

    public SuccessResponse deleteEmployee(CallContext call, String employeeId) throws IOException {
        Response response = deleteEmployee(call, employeeId, HttpStatus.SC_OK);
        return SerializationHelper.deserialize(asString(response), SuccessResponse.class);
    }

    public Response deleteEmployee(CallContext call, String employeeId, int statusCode) {
        return RequestHelper.delete(call, String.format(ServicePath.DELETE_EMPLOYEE_URI, employeeId), statusCode);
    }

}
