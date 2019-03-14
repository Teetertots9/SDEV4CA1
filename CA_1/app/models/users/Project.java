package models.users;


import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Table(name = "project")
@Entity

public class Project{
    @Id
    String projName;
    
    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "projects")
    private List<Employee> employees = new ArrayList<>();
    

    public Project(){

    }
    public Project(String name, List<Employee> employees){
        this.projName = name;
        this.employees = employees;
    }

    public void setProjName(String name){
        this.projName = name;
    }
    public void setEmployees(List<Employee> emps){
        employees = emps;
    }
    public String getProjName(){
        return projName;
    }
    public List<Employee> getEmployees(){
        return employees;
    }

    public static final Finder<Long, Project> find = new Finder<>(Project.class);

    public static final List<Project> findAll() {
        return Project.find.all();
    }

}