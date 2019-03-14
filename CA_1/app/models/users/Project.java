package models.users;


import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;



public class Project{
    
    String name;
    
    
    private List<Employee> employees = new ArrayList<>();
    

    public Project(){

    }
    public Project(String name, List<Employee> employees){
        this.name = name;
        this.employees = employees;
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


}