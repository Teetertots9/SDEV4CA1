package models.users;


import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Table(name = "project")
@Entity

public class Project extends Model{
    @Id
    private Long id;

    @Constraints.Required
    String projName;
    
    @ManyToMany(cascade=CascadeType.ALL, mappedBy = "projects")
    private List<Employee> employees = new ArrayList<>();
    

    public Project(){

    }
    public Project(Long id, String name, List<Employee> employees){
        this.id = id;
        this.projName = name;
        this.employees = employees;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return find.all();
    }

    public static Project getProjectById(Long id) {
        if (id == null) {
            return null;
        } else {
            return find.query().where().eq("id", id).findUnique();
        }
    }
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap();
    for (Project p: Project.findAll()) {
        options.put(p.getId().toString(), p.getProjName());
     }
     return options;
  }
  public static boolean inProject(String project, String employee) {
     return find.query().where().eq("employees.email", employee)
                        .eq("projName", project)
                        .findList().size() > 0;
 }
}