package com.lms.model;

import com.lms.configs.JavaDateMySqlDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    private  Integer id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=3,message = "first name size be must greater than or equal to 3 chars")
    private String firstName;

    @NotNull
    @Size(min=3,message = "last name size be must greater than or equal to 3 chars")
    private String lastName;

    @NotNull
    @Size(min=6,message = "password length must be greater than or equal to 6 chars")
    private String password;

    @NotNull
    @Size(min=6,message = "address length must be greater than or equal to 6 chars")
    private String address;

    private final String created_date = new JavaDateMySqlDate().getDateTime();
    private final String updated_date = new JavaDateMySqlDate().getDateTime();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", created_date='" + created_date + '\'' +
                ", updated_date='" + updated_date + '\'' +
                '}';
    }
}
