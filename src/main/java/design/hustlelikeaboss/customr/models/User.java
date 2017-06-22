package design.hustlelikeaboss.customr.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by quanjin on 6/20/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=3, max=15)
    private String password;

    @NotNull
    @Size(min=3, max=15)
    private String verify;

// constructors


    public User(String email, String password, String verify) {
        this.email = email;
        this.password = password;
        this.verify = verify;
    }

    public User() {
    }

//  setters & getters

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }
}
