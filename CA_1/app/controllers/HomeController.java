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

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private FormFactory formFactory;

    @Inject
    public HomeController(FormFactory f) {
        this.formFactory = f;
}
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result onsale(Long cat) {
        List<ItemOnSale> itemList = null;
        List<Category> categoryList = Category.findAll();

        if(cat ==0){
            itemList = ItemOnSale.findAll();
        }else {
            itemList = Category.find.ref(cat).getItems();
        }
        return ok(onsale.render(itemList, categoryList,User.getUserById(session().get("email"))));

     }

    public Result index() {
        return ok(index.render(User.getUserById(session().get("email"))));
    }

    public Result about() {
        return ok(about.render(User.getUserById(session().get("email"))));
    }
    @Security.Authenticated(Secured.class)
    public Result addItem() {
        Form<ItemOnSale> itemForm = formFactory.form(ItemOnSale.class);
        return ok(addItem.render(itemForm,User.getUserById(session().get("email"))));
}
@Security.Authenticated(Secured.class)
@Transactional
public Result addItemSubmit() {
    // We use the method bindFromRequest() to populate our Form<ItemOnSale> object with the
    // data that the user submitted. Thanks to Play Framework, we do not need to do the messy
    // work of parsing the request and extracting data from it characte by character.
    Form<ItemOnSale> newItemForm = formFactory.form(ItemOnSale.class).bindFromRequest();
    // We check for errors (based on constraints set in ItemOnSale class)
    if (newItemForm.hasErrors()) {
        // If the form data have errors, we call the method badRequest(), requesting Play 
        // Framework to send an error response to the user and display the additem page again. 
        // As we are passing in newItemForm, the form will be populated with the data that the 
        // user has already entered, saving them from having to enter it all over again.
        return badRequest(addItem.render(newItemForm,User.getUserById(session().get("email"))));
    } else {
        // If no errors are found in the form data, we extract the data from the form.
        // Form objects have handy utility methods, such as the get() method we are using 
        // here to extract the data into an ItemOnSale object. This is possible because
        // we defined the form in terms of the model class ItemOnSale.
        ItemOnSale newItem = newItemForm.get();
        // Now we call the ORM method save() on the model object, to have it saved in the
        // database as a line in the table item_on_sale.
        
        if(newItem.getId()==null){
        newItem.save();
        }else{
            newItem.update();
        }
        // We use the flash scope to specify that we want a success message superimposed on
        // the next displayed page. The flash scope uses cookies, which we can read and set
        // using the flash() function of the Play Framework. The flash scope cookies last
        // for a single request (unlike session cookies, which we will use for log-in in a
        // future lab). So, add a success message to the flash scope.
        flash("success", "Item " + newItem.getName() + " was added/updated.");
        // Having specified we want a message at the top, we can redirect to the onsale page,
        // which will have to be modified to read the flash scope and display it.
        return redirect(controllers.routes.HomeController.onsale(0));
    }
}
@Security.Authenticated(Secured.class)
@Transactional
@With(AuthAdmin.class)
public Result deleteItem(Long id) {

    // The following line of code finds the item object by id, then calls the delete() method
    // on it to have it removed from the database.
    ItemOnSale.find.ref(id).delete();

    // Now write to the flash scope, as we did for the successful item creation.
    flash("success", "Item has been deleted.");
    // And redirect to the onsale page
    return redirect(controllers.routes.HomeController.onsale(0));
}
@Security.Authenticated(Secured.class)
public Result updateItem(Long id) {
    ItemOnSale i;
    Form<ItemOnSale> itemForm;

    try {
        // Find the item by id
        i = ItemOnSale.find.byId(id);

        // Populate the form object with data from the item found in the database
        itemForm = formFactory.form(ItemOnSale.class).fill(i);
    } catch (Exception ex) {
        return badRequest("error");
    }

    // Display the "add item" page, to allow the user to update the item
    return ok(addItem.render(itemForm,User.getUserById(session().get("email"))));
}
@Security.Authenticated(Secured.class)
@Transactional
@With(AuthAdmin.class)
public Result deleteUser(String email) {

        User u = User.getUserById(email);
        u.delete();

    flash("success", "User has been deleted.");

    return redirect(controllers.routes.HomeController.users());
}
@Security.Authenticated(Secured.class)
public Result updateUser(String email) {
    User u;
    Form<User> userForm;

    try {
 
        u = User.getUserById(email);
        u.update();


        userForm = formFactory.form(User.class).fill(u);
    } catch (Exception ex) {
        return badRequest("error");
    }


    return ok(addUser.render(userForm,User.getUserById(session().get("email"))));
}

@Security.Authenticated(Secured.class)
public Result addUser() {
    Form<User> userForm = formFactory.form(User.class);
    return ok(addUser.render(userForm,User.getUserById(session().get("email"))));
}
@Security.Authenticated(Secured.class)
@Transactional
public Result addUserSubmit() {

Form<User> newUserForm = formFactory.form(User.class).bindFromRequest();

if (newUserForm.hasErrors()) {

    return badRequest(addUser.render(newUserForm,User.getUserById(session().get("email"))));
} else {

    User newUser = newUserForm.get();

    
    if(User.getUserById(newUser.getEmail())==null){
        newUser.save();
    }else{
        newUser.update();
    }

    flash("success", "User " + newUser.getName() + " was added/updated.");

    return redirect(controllers.routes.HomeController.users()); 
    }
}

public Result users() {
    List<User> userList = null;

    userList = User.findAll();

    return ok(users.render(userList,User.getUserById(session().get("email"))));

 }

}