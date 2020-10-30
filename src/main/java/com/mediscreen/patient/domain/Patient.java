package com.mediscreen.patient.domain;

import com.mediscreen.patient.constant.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Class materializing a patient.
 */
@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 125, message = "Maximum length = 125 characters")
    @NotBlank(message = "LastName is mandatory")
    @Column(nullable = false)
    private String lastName;

    @Size(max = 125, message = "Maximum length = 125 characters")
    @NotBlank(message = "FirstName is mandatory")
    @Column(nullable = false)
    private String firstName;

    @Size(max = 125, message = "Maximum length = 125 characters")
    @NotBlank(message = "DateOfBirth is mandatory")
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Sex is mandatory")
    @Column(nullable = false, length = 1)
    private Sex sex;

    @Size(max = 255, message = "Maximum length = 255 characters")
    @NotBlank(message = "HomeAddress is mandatory")
    @Column(nullable = false)
    private String homeAddress;

    @NotBlank(message = "PhoneNumber is mandatory")
    @Column(nullable = false)
    @Pattern(regexp = "^(\\d{3})(\\d{3})(\\d{4}).-", message = "Phone Number : 111-111-1111")
    private String phoneNumber;

    public Patient() {
    }

    public Patient(@Size(max = 125, message = "Maximum length = 125 characters") @NotBlank(message = "LastName is mandatory") String lastName, @Size(max = 125, message = "Maximum length = 125 characters") @NotBlank(message = "FirstName is mandatory") String firstName, @Size(max = 125, message = "Maximum length = 125 characters") @NotBlank(message = "DateOfBirth is mandatory") Date dateOfBirth, @NotBlank(message = "Sex is mandatory") Sex sex, @Size(max = 255, message = "Maximum length = 255 characters") @NotBlank(message = "HomeAddress is mandatory") String homeAddress, @NotBlank(message = "PhoneNumber is mandatory") @Pattern(regexp = "^(\\d{3})(\\d{3})(\\d{4}).-", message = "Phone Number : 111-111-1111") String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
