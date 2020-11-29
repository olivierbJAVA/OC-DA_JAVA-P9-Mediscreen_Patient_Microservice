package com.mediscreen.patient.domain;

import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.constant.Assessment;

import java.io.Serializable;

/**
 * Class materializing a patient diabetes risk assessment report.
 */
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lastName;
    private String firstName;
    private Sex sex;
    private long age;

    private Assessment assessment;

    public Rapport() {
    }

    public Rapport(String lastName, String firstName, Sex sex, long age, Assessment assessment) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.sex = sex;
        this.age = age;
        this.assessment = assessment;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}
