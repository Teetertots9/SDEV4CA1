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
    public Result usersEmployee(){
        List<Employee> userList = null;

        userList = Employee.findAll();

        return ok(employees.render(userList,User.getUserById(session().get("email"))));
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
    @Transactional
    @With(AuthManager.class)
    public Result deleteEmployee(String email) {

        Employee emp = (Employee) User.getUserById(email);
        emp.delete();

        flash("success", "Employee has been deleted.");
        return redirect(controllers.routes.EmployeeController.usersEmployee());
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
    
            if(User.getUserById(newUser.getEmail())==null){
                newUser.save();
            }else{
                newUser.update();
            }
            flash("success", "Employee " + newUser.getName() + " was added/updated.");
            return redirect(controllers.routes.EmployeeController.usersEmployee());             
        }
    }

    //END Employee Methods
}