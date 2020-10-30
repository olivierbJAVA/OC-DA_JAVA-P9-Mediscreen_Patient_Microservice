package com.mediscreen.patient.domain;

import com.mediscreen.patient.constant.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Patient {

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
}
