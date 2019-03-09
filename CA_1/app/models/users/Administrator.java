package models.users;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;


@Table(name="User")
// the user type of this class is "admin"
@DiscriminatorValue("a")
@Entity
public class Administrator extends User {

    public Administrator(){

    }
    public Administrator(String email, String name,String role, String password) {
        super(email, name, role,password);
    }

    public static final Finder<Long, Administrator> find = new Finder<>(Administrator.class);
			    
    public static final List<Administrator> findAll() {
       return Administrator.find.all();
    }
}