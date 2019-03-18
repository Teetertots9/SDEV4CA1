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
    

        return ok(profile.render(User.getUserById(session().get("email")),e));
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
        

        try {
            emp = (Employee) User.getUserById(email);
            emp.update();

            employeeForm = formFactory.form(Employee.class).fill(emp);
            
        } catch (Exception ex) {
            return badRequest("error");
        }

        return ok(assignProjectToEmployee.render(emp,employeeForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    @With(AuthManager.class)
    @Transactional
    public Result assignProjectToEmployeeSubmit(){
        Form<Employee> assignEmployeeForm = formFactory.form(Employee.class).bindFromRequest();
        Employee emp = assignEmployeeForm.get();

        //Also not working, more null pointer exception. Can't think of any more ways to just use the ID from the form and get all the other data from the database or models.
        //I even tried passing an employee into this method but got an error there too on the form view page.

        //Employee emp = (Employee) User.getUserById(assignEmployeeForm.get().getEmail());

        //Doesn't work at all, no idea how to fix it, constant null pointer excetpion no matter what I try. So stuck with terrible looking form for now.
 //       Employee empTemp = assignEmployeeForm.get();
  //      Employee emp = (Employee) User.getUserById(empTemp.getEmail())    //Looks like a mess but just might work. Trying to only pull the ID out of the form and using that to populate the Employee object
        if (assignEmployeeForm.hasErrors()) {
            return badRequest(assignProjectToEmployee.render(emp,assignEmployeeForm,User.getUserById(session().get("email"))));
        } else {
            

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

            MultipartFormData<File> data = request().body().asMultipartFormData();
       
        FilePart<File> image = data.getFile("upload");
    
        String saveUserMessage = saveFileP(newUser.getEmail(), image);
            flash("success", "Employee " + newUser.getName() + " was added/updated.");
            return redirect(controllers.routes.HomeController.index());             
        }
    }

    public Result addProfileImage(String email){
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

        return ok(addProfileImage.render(employeeForm,addressForm, User.getUserById(session().get("email"))));
    }
    public String saveFileP(String id, FilePart<File> uploaded) {
        
        if (uploaded != null) {
            
            String mimeType = uploaded.getContentType();
            if (mimeType.startsWith("image/")) {
                
                String fileName = uploaded.getFilename();
                
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i >= 0) {
                    extension = fileName.substring(i + 1);
                }
                
                File file = uploaded.getFile();
                
                File dir = new File("public/images/profileImages");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 3) Actually save the file.
                File newFile = new File("public/images/profileImages/", id + "." + extension);
                if (file.renameTo(newFile)) {
                    try {
                        BufferedImage img = ImageIO.read(newFile); 
                        BufferedImage scaledImg = Scalr.resize(img, 300);
                        
                        if (ImageIO.write(scaledImg, extension, new File("public/images/profileImages/", id + "profile.jpg"))) {
                            return "/ file uploaded and Profile created.";
                        } else {
                            return "/ file uploaded but profile creation failed.";
                        }
                    } catch (IOException e) {
                        return "/ file uploaded but profile creation failed.";
                    }
                } else {
                    return "/ file upload failed.";
                }
    
            }
        }
        return "/ no image file.";
    }
    //END Employee Methods
}