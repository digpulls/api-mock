package testdata.employeetests;

public class CreateEmployeeRequest {

    private String name;
    private Integer salary;
    private Integer age;

    public String getName() {
        return name;
    }

    public CreateEmployeeRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSalary() {
        return salary;
    }

    public CreateEmployeeRequest setSalary(Integer salary) {
        this.salary = salary;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public CreateEmployeeRequest setAge(Integer age) {
        this.age = age;
        return this;
    }
}
