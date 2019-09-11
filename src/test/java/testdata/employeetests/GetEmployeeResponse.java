package testdata.employeetests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetEmployeeResponse {

    private String id;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_salary")
    private String employeeSalary;

    @JsonProperty("employee_age")
    private String employeeAge;


    @JsonProperty("profile_image")
    private String profileImage;

    public String getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
