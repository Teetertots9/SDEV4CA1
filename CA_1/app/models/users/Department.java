package models.users;


import java.util.*;
import javax.persistence.*;

import io.ebean.*;

@Table(name = "department")
@Entity
public class Department {
    @Id
    private String name;

    @OneToMany(mappedBy="department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public Department(String name, List<Employee> emps) {
        this.name = name;
        employees = emps;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmployees(List<Employee> emps){
        employees = emps;
    }
    public String getName(){
        return name;
    }
    public List<Employee> getEmployees(){
        return employees;
    }
    public void addEmployee(Employee emp){
        employees.add(emp);
    }
    public void removeEmployee(Employee emp){
        employees.remove(emp);
    }

    public static final Finder<Long, Department> find = new Finder<>(Department.class);

    public static final List<Department> findAll() {
        return Department.find.all();
    }
}