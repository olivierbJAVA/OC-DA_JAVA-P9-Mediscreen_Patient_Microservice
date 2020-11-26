package com.mediscreen.patient.domain;

import com.mediscreen.patient.constant.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

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

    @NotNull(message = "DateOfBirth is mandatory")
    @Column(nullable = false, columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sex is mandatory")
    @Column(nullable = false, length = 1)
    private Sex sex;

    @Size(max = 255, message = "Maximum length = 255 characters")
    @Column(nullable = true)
    private String homeAddress;

    //@Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}", message = "Phone Number format : XXX-XXX-XXXX")
    @Column(nullable = true)
    private String phoneNumber;

    public Patient() {
    }

    public Patient(@Size(max = 125, message = "Maximum length = 125 characters") @NotBlank(message = "LastName is mandatory") String lastName, @Size(max = 125, message = "Maximum length = 125 characters") @NotBlank(message = "FirstName is mandatory") String firstName, @NotNull(message = "DateOfBirth is mandatory") LocalDate dateOfBirth, @NotNull(message = "Sex is mandatory") Sex sex, @Size(max = 255, message = "Maximum length = 255 characters") String homeAddress, String phoneNumber) {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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
