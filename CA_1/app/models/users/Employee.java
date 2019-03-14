package models.users;


import java.util.*;
import javax.persistence.*;
import io.ebean.*;

@Table(name = "user")
@DiscriminatorValue("e")
@Entity

public class Employee extends User {
    
    @ManyToOne
    private Department department;

    @OneToOne(mappedBy="employee", cascade = CascadeType.ALL)
    private Address address;

    private List<Project> projects = new ArrayList<>();

    public Employee() {
    }

    public Employee(String email, String role, String name, String password) {
        super(email, role, name, password);
    }

    public Employee(String email, String role, String name, String password, Date dob, Department dep, Address address,
            List<Project> projects) {
        super(email, role, name, password, dob);
        this.department = dep;
        this.address = address;
        this.projects = projects;
    }
    public void setDepartment(Department dep){
        this.department = dep;
    }
    public void setProjects(List<Project> projects){
        this.projects = projects;
    }
    public void setAddress(Address address){
        this.address = address;
    }

    public Department getDepartment(){
        return department;
    }
    public List<Project> getProjects(){
        return projects;
    }
    public Address getAddress(){
        return address;
    }

    public void assignToProject(Project proj){
        projects.add(proj);
    }
    public void removeFromProject(Project proj){
        projects.remove(proj);
    }

    public static final Finder<Long, Employee> find = new Finder<>(Employee.class);

    public static final List<Employee> findAll() {
        return Employee.find.all();
    }
}