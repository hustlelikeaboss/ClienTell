package design.hustlelikeaboss.customr.models;

import org.hibernate.validator.constraints.Email;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Pattern;

/**
 * Created by quanjin on 6/20/17.
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private int id;

    private String fName;

    private String lName;

    @Email
    private String email;

    @Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}")
    private String phoneNumber;

//  constructors
    public Customer(String fName, String lName, String email, String phoneNumber) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String email) {
        this.email = email;
    }

    public Customer(String fName, String email) {
        this.fName = fName;
        this.email = email;
    }

    public Customer() {
    }

    //  getters & setters
    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
