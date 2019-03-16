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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="AID")
    private Address address;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Project> projects;

    private List<Long> projSelect = new ArrayList<Long>();

    public Employee() {
    }

    public Employee(String email, String role, String name, String password) {
        super(email, role, name, password);
    }

    public Employee(String email, String role, String name, String password, Department dep, Address address,
            List<Project> projects) {
        super(email, role, name, password);
        this.department = dep;
        this.address = address;
        this.projects = projects;
    }
    public Employee(String email, String role, String name, String password, Department dep, Address address){
        super(email, role, name, password);
        this.department = dep;
        this.address = address;
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
        this.projects.add(proj);
        proj.getEmployees().add(this);
    }
    public void removeFromProject(Project proj){
        this.projects.remove(proj);
        proj.getEmployees().remove(this);
    }

    public static final Finder<Long, Employee> find = new Finder<>(Employee.class);

    public static final List<Employee> findAll() {
        return Employee.find.all();
    }

    public List<Long> getProjSelect() {
        return projSelect;
    }
    public void setProjSelect(List<Long> projSelect) {
        this.projSelect = projSelect;
    }
}