package models.users;

import models.users.*;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

public class Address{
     private int addressID;
    private String street1;
    private String street2;
    private String town;
    private String postCode;
     private Employee employee;

    public Address(){

    }
    public Address(String add1, String add2, String town,String post){
        street1=add1;
        street2=add2;
        this.town = town;
        postCode = post;
    }

    public void setStreet1(String street1){
        this.street1 = street1;
    }
    public void setStreet2(String street2){
        this.street2=street2;
    }
    public void setTown(String town){
        this.town = town;
    }
    public void setPostCode(String pc){
        postCode = pc;
    }

    public String getStreet1(){
        return street1;
    }
    public String getStreet2(){
        return street2;
    }
    public String getTown(){
        return town;
    }
    public String getPostCode(){
        return postCode;
    }


}