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

public class ProjectController extends Controller{
    private FormFactory formFactory;
    private Environment e;
    
    @Inject
    public ProjectController(FormFactory f,Environment env) {
        
        this.formFactory = f;
        this.e = env;
    }

    //Begin Employee methods
    public Result projects(Long proj){
        List<Project> projList = Project.findAll();
        List<Employee> userList = null;
        if(proj.equals(0)){
            userList = Employee.findAll();
        }else {
            userList = Project.find.ref(proj).getEmployees();
        }
        
        

        return ok(projects.render(projList,userList, User.getUserById(session().get("email"))));
    }


    @Security.Authenticated(Secured.class)
    public Result updateProject(Long id) {
        Project project;
        Form<Project> projectForm;

        try {
            project = Project.getProjectById(id);
            project.update();

            projectForm = formFactory.form(Project.class).fill(project);
            
        } catch (Exception ex) {
            return badRequest("error");
        }

        return ok(addProject.render(projectForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    @With(AuthManager.class)
    public Result deleteProject(Long id) {

        Project proj = Project.getProjectById(id);
        proj.delete();

        flash("success", "Project has been deleted.");
        return redirect(controllers.routes.ProjectController.projects(0));
    }

    @Security.Authenticated(Secured.class)
    public Result addProject() {
        Form<Project> projectForm = formFactory.form(Project.class);
        
        return ok(addProject.render(projectForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    @With(AuthManager.class)
    @Transactional
    public Result addProjectSubmit(){
        Form<Project> newProjectForm = formFactory.form(Project.class).bindFromRequest();
        
        if (newProjectForm.hasErrors()) {
            return badRequest(addProject.render(newProjectForm,User.getUserById(session().get("email"))));
        } else {
            Project newProject = newProjectForm.get();
            
            
    
            if(Project.getProjectById(newProject.getId())==null){
                newProject.save();
            }else{
                newProject.update();
            }
            flash("success", "Project " + newProject.getProjName() + " was added/updated.");
            return redirect(controllers.routes.ProjectController.projects(0));             
        }
    }

    //END Project Methods
}