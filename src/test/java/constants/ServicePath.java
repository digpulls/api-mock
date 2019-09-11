package constants;

public class ServicePath {

    private static final String BASE_SERVICE_PATH = "/service";
    public static final String GET_AVAILABLE_CURRENCY = BASE_SERVICE_PATH + "/available_currencies";
    public static final String GET_CONVERTED_CURRENCY = BASE_SERVICE_PATH + "/convert";

    private static final String BASE_EMPLOYEE_PATH = "/api/v1";
    public static final String CREATE_EMPLOYEE_URI = BASE_EMPLOYEE_PATH + "/create";
    public static final String DELETE_EMPLOYEE_URI = BASE_EMPLOYEE_PATH + "/delete/%s";
    public static final String GET_EMPLOYEE_URI = BASE_EMPLOYEE_PATH + "/employee/%s";
    public static final String GET_EMPLOYEE_ALL_URI = BASE_EMPLOYEE_PATH + "/employees";

}
