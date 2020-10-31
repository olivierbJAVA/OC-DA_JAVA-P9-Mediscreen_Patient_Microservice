package com.mediscreen.patient.controller;

import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller in charge of managing the endpoints for Patient entities.
 */
@Controller
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Method managing the GET "/patients" endpoint HTTP request to get the list of all patients.
     *
     * @param model The Model containing the list of all patients
     * @return The name of the View
     */
    @GetMapping("/patients")
    public String getPatients(Model model) {

        logger.info("Request : GET /patients");

        List<Patient> patients = patientService.findAllPatients();
        model.addAttribute("patients", patients);

        logger.info("Success : patients found, returning 'patients' view");

        return "patients";
    }

    /**
     * Method managing the GET "/patients/{id}" endpoint HTTP request to get a patient given its id.
     *
     * @param id The id of the patient to get
     * @param model The Model containing the patient to get
     * @return The name of the View
     */
    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable("id") Long id, Model model) {

        logger.info("Request : GET /patients/{}", id);

        Patient patient = patientService.findPatientById(id);
        model.addAttribute("patients", patient);

        logger.info("Success : patient with id {} found, returning '/patients' view", id);

        return "patients";
    }

    /**
     * Method managing the GET "/patientsByLastName" endpoint HTTP request to get patients given the last name.
     *
     * @param lastName The last name of the patients to get
     * @param model The Model containing the patients to get
     * @return The name of the View
     */
    @GetMapping("/patientsByLastName")
    public String getPatientsByLastName(@RequestParam("lastName") String lastName, Model model) {

        logger.info("Request : GET /patientsByLastName with last name = {}", lastName);

        List<Patient> patients = patientService.findPatientsByLastName(lastName);
        model.addAttribute("patients", patients);

        logger.info("Success : patient(s) with last name = {} found, returning '/patients' view", lastName);

        return "patients";
    }

    /**
     * Method managing the GET "/patientByLastNameAndFirstName" endpoint HTTP request to get a patient given its last name and first name.
     *
     * @param lastName The last name of the patient to get
     * @param firstName The first name of the patient to get
     * @param model The Model containing the patient to get
     * @return The name of the View
     */
    @GetMapping("/patientByLastNameAndFirstName")
    public String getPatientByLastNameAndFirstName(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName, Model model) {

        logger.info("Request : GET /patientByLastNameAndFirstName with last name = {} & first name = {}", lastName, firstName);

        Patient patient = patientService.findPatientByLastNameAndFirstName(lastName, firstName);
        model.addAttribute("patients", patient);

        logger.info("Success : patient with last name {} and first name {} found, returning '/patients' view", lastName, firstName);

        return "patients";
    }

}
