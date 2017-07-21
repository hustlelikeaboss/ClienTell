package design.hustlelikeaboss.customr.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

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

    private String company;
    private String website;
    private String streetAddress;
    private String city;
    private String state;
    private String Country;
    private String zipCode;

    // assign customers to user
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany
    private List<Project> projects = new ArrayList<>();

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

    public Customer(String fName, String lName, String email) {
        this.fName = fName;
        this.lName = lName;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
