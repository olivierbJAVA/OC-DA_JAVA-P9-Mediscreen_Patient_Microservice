package com.mediscreen.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class in charge of launching the Patient Microservices in charge of managieng patient data.
 */
@SpringBootApplication
public class PatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

}
