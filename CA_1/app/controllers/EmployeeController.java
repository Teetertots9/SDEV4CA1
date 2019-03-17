package controllers;

import play.mvc.*;

import views.html.*;

import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import models.*;
import models.users.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

import java.io.IOException;
import java.awt.image.*;
import javax.imageio.*;
import org.imgscalr.*;

public class EmployeeController extends Controller{
    private FormFactory formFactory;
    private Environment e;
    
    @Inject
    public EmployeeController(FormFactory f,Environment env) {
        
        this.formFactory = f;
        this.e = env;
    }

    //Begin Employee methods
    public Result usersEmployee(Long department){
        List<Employee> userList = null;
        List<Department> departmentList = Department.findAll();

        if(department == 0){
            userList = Employee.findAll();
        }else{
            userList = Department.find.ref(department).getEmployees();
        }

        return ok(employees.render(userList,departmentList,User.getUserById(session().get("email"))));
    }

    public Result profile(){
    

        return ok(profile.render(User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    public Result updateEmployee(String email) {
        Employee emp;
        Form<Employee> employeeForm;
        Form<Address> addressForm;

        try {
            emp = (Employee) User.getUserById(email);
            emp.update();

            employeeForm = formFactory.form(Employee.class).fill(emp);
            addressForm = formFactory.form(Address.class).fill(emp.getAddress());
        } catch (Exception ex) {
            return badRequest("error");
        }

        return ok(addEmployee.render(employeeForm,addressForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    public Result assignProjectToEmployee(String email) {
        Employee emp;
        Form<Employee> employeeForm;
        Form<Address> addressForm;
        

        try {
            emp = (Employee) User.getUserById(email);
            emp.update();

            employeeForm = formFactory.form(Employee.class).fill(emp);
            addressForm = formFactory.form(Address.class).fill(emp.getAddress());
        } catch (Exception ex) {
            return badRequest("error");
        }

        return ok(assignProjectToEmployee.render(emp,employeeForm,addressForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    @With(AuthManager.class)
    @Transactional
    public Result assignProjectToEmployeeSubmit(){
        Form<Employee> assignEmployeeForm = formFactory.form(Employee.class).bindFromRequest();
        Form<Address> addressForm = formFactory.form(Address.class).bindFromRequest();
        Employee emp = assignEmployeeForm.get();
        if (assignEmployeeForm.hasErrors()) {
            return badRequest(assignProjectToEmployee.render(emp,assignEmployeeForm,addressForm,User.getUserById(session().get("email"))));
        } else {
            
            Address address = addressForm.get();
            emp.setAddress(address);

            List<Project> newProjs = new ArrayList<Project>();
        for (Long proj : emp.getProjSelect()) {
            newProjs.add(Project.find.byId(proj));
        }
        emp.setProjects (newProjs);
    
            if(User.getUserById(emp.getEmail())==null){
                emp.save();
            }else{
                emp.update();
            }
            flash("success", "Employee " + emp.getName() + " was added/updated.");
            return redirect(controllers.routes.EmployeeController.usersEmployee(0));             
        }
    }



    @Security.Authenticated(Secured.class)
    @Transactional
    @With(AuthManager.class)
    public Result deleteEmployee(String email) {

        Employee emp = (Employee) User.getUserById(email);
        emp.delete();

        flash("success", "Employee has been deleted.");
        return redirect(controllers.routes.EmployeeController.usersEmployee(0));
    }

    @Security.Authenticated(Secured.class)
    public Result addEmployee() {
        Form<Employee> userForm = formFactory.form(Employee.class);
        Form<Address> addressForm = formFactory.form(Address.class);
        return ok(addEmployee.render(userForm,addressForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    @With(AuthManager.class)
    @Transactional
    public Result addEmployeeSubmit(){
        Form<Employee> newEmployeeForm = formFactory.form(Employee.class).bindFromRequest();
        Form<Address> newAddressForm = formFactory.form(Address.class).bindFromRequest();
        if (newEmployeeForm.hasErrors()) {
            return badRequest(addEmployee.render(newEmployeeForm,newAddressForm,User.getUserById(session().get("email"))));
        } else {
            Employee newUser = newEmployeeForm.get();
            Address address = newAddressForm.get();
            newUser.setAddress(address);

            List<Project> newProjs = new ArrayList<Project>();
        for (Long proj : newUser.getProjSelect()) {
            newProjs.add(Project.find.byId(proj));
        }
        newUser.setProjects (newProjs);
    
            if(User.getUserById(newUser.getEmail())==null){
                newUser.save();
            }else{
                newUser.update();
            }
            flash("success", "Employee " + newUser.getName() + " was added/updated.");
            return redirect(controllers.routes.EmployeeController.usersEmployee(0));             
        }
    }

    //END Employee Methods
}