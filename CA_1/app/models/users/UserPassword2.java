package models.users;

public class UserPassword2 extends User {
    private String password2;


    public UserPassword2(){

    }

    public UserPassword2(String email, String role, 
            String name, String password1, String password2) {
        super(email,role,name, password1);
        this.password2 = password2;
    }

    public String getPassword2(){
        return password2;
    }

    public void setPassword2(String p){
        this.password2 = p;
    }

}