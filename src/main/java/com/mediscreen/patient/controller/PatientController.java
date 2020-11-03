package com.mediscreen.patient.controller;

import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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
     * Method managing the GET "/patients/list" endpoint HTTP request to get the list of all patients.
     *
     * @param model The Model containing the list of all patients
     * @return The name of the View
     */
    @GetMapping("/patients/list")
    public String getPatients(Model model) {

        logger.info("Request : GET /patients/list");

        List<Patient> patients = patientService.findAllPatients();
        model.addAttribute("patients", patients);

        logger.info("Success : patients found, returning 'patients/list' view");

        return "patients/list";
    }

    /**
     * Method managing the GET "/patients/update/{id}" endpoint HTTP request to update a patient.
     *
     * @param id The id of the patient to update
     * @param model The Model containing the patient to update
     * @return The name of the View
     */
    @GetMapping("/patients/update/{id}")
    public String showPatientUpdateForm(@PathVariable("id") Long id, Model model) {

        logger.info("Request : GET /patients/update/{}", id);

        Patient patient = patientService.findPatientById(id);
        model.addAttribute("patient", patient);

        logger.info("Success : patient with id {} to update found, returning '/patient/update' view", id);

        return "patients/update";
    }

    /**
     * Method managing the POST "/patients/update/{id}" endpoint HTTP request to update a patient.
     *
     * @param patient The patient to update
     * @param result The BindingResult containing the result of the fields validation
     * @return The name of the View
     */
    @PostMapping("/patients/update/{id}")
    public String updatePatient(@Valid Patient patient, BindingResult result) {

        logger.info("Request : POST /patients/update/{}", patient.getId());

        if (result.hasErrors()) {

            logger.error("Error in fields validation : patient with id {} not updated, returning '/patients/update' view", patient.getId());

            return "patients/update";
        }

        patientService.updatePatient(patient);

        logger.info("Success : patient with id {} updated, redirect to '/patients/list", patient.getId());

        return "redirect:/patients/list";
    }

    /**
     * Method managing the GET "/patients/add" endpoint HTTP request to add a patient.
     *
     * @param patient An empty patient
     * @return The name of the View
     */
    @GetMapping("/patients/add")
    public String addPatientForm(Patient patient) {

        logger.info("Request : GET /patients/add");
        logger.info("Success : returning 'patients/add' view");

        return "patients/add";
    }

    /**
     * Method managing the POST "/patients/validate" endpoint HTTP request to add a patient.
     *
     * @param patient The patient to add
     * @param result The BindingResult containing the result of the fields validation
     * @return The name of the View
     */
    @PostMapping("/patients/validate")
    public String validatePatient(@Valid Patient patient, BindingResult result) {

        logger.info("Request : POST /patients/validate");

        if (!result.hasErrors()) {
            patientService.createPatient(patient);

            logger.info("Success : new patient created, redirect to '/patients/list' view");

            return "redirect:/patients/list";
        }

        logger.error("Error in fields validation : new patient not created, returning '/patients/add' view");

        return "patients/add";
    }
}
