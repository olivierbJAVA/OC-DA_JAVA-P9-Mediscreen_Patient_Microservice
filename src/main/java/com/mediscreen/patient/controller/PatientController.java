package com.mediscreen.patient.controller;

import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Rapport;
import com.mediscreen.patient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.*;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller in charge of managing the endpoints for Patient entities.
 */
@Controller
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Value("${noteMicroserviceUrl}")
    private String noteMicroserviceUrl;

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Method managing the GET "/patients/list" endpoint HTTP request to get the list of all patients in a HTML page.
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
     * Method managing the GET "/patients/patientByFamilyAndGiven" endpoint HTTP request to get a patient given its last name and first name in JSON data.
     *
     * @param lastName The last name of the patient to get
     * @param firstName The first name of the patient to get
     * @return A ResponseEntity containing the patient and the HTTP status code
     */
    @GetMapping("/patients/patientByFamilyAndGiven")
    public ResponseEntity<Patient> getPatientByLastNameAndFirstName(@RequestParam("family") String lastName, @RequestParam("given") String firstName) {

        logger.info("Request : GET /patients/patientByFamilyAndGiven with last name = {} & first name = {}", lastName, firstName);

        Patient patient = patientService.findPatientByLastNameAndFirstName(lastName, firstName);

        logger.info("Success : patient with last name {} and first name {} found", lastName, firstName);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /**
     * Method managing the GET "/patients/patientById" endpoint HTTP request to get a patient given its identifier in JSON data.
     *
     * @param id The identifier of the patient to get
     * @return A ResponseEntity containing the patient and the HTTP status code
     */
    @GetMapping("/patients/patientById")
    public ResponseEntity<Patient> getPatientById(@RequestParam("id") long id) {

        logger.info("Request : GET /patients/patientById with id = {}", id);

        Patient patient = patientService.findPatientById(id);

        logger.info("Success : patient with id {} found", id);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /**
     * Method managing the GET "/patients/updateform/{id}" endpoint HTTP request to update a patient using a HTML form.
     *
     * @param id The id of the patient to update
     * @param model The Model containing the patient to update
     * @return The name of the View
     */
    @GetMapping("/patients/updateform/{id}")
    public String showPatientUpdateForm(@PathVariable("id") Long id, Model model) {

        logger.info("Request : GET /patients/updateform/{}", id);

        Patient patient = patientService.findPatientById(id);
        model.addAttribute("patient", patient);

        logger.info("Success : patient with id {} to update found, returning '/patient/updateform' view", id);

        return "patients/updateform";
    }

    /**
     * Method managing the POST "/patients/updateform/{id}" endpoint HTTP request to update a patient using a HTML form.
     *
     * @param patient The patient to update
     * @param result The BindingResult containing the result of the fields validation
     * @return The name of the View
     */
    @PostMapping("/patients/updateform/{id}")
    public String updatePatientForm(@Valid Patient patient, BindingResult result) {

        logger.info("Request : POST /patients/updateform/{}", patient.getId());

        if (result.hasErrors()) {

            logger.error("Error in fields validation : patient with id {} not updated, returning '/patients/updateform' view", patient.getId());

            return "patients/updateform";
        }

        patientService.updatePatient(patient);

        logger.info("Success : patient with id {} updated, redirect to '/patients/list", patient.getId());

        return "redirect:/patients/list";
    }

    /**
     * Method managing the POST "/patients/update" endpoint HTTP request to update a patient using a command line HTTP client and parameters in the URL request.
     *
     * @param family The last name of the patient
     * @param given The first name of the patient
     * @param dob The date of birth of the patient
     * @param sex The sex of the patient
     * @param address The home address of the patient
     * @param phone The phone number of the patient
     * @return A ResponseEntity containing the updated patient and the HTTP status code
     */
    @PostMapping("/patients/update")
    public ResponseEntity<Patient> updatePatient(@RequestParam(required = true) String family, @RequestParam(required = true) String given, @RequestParam(required = true) String dob, @RequestParam(required = true) Sex sex, @RequestParam(required = false) String address, @RequestParam(required = false) String phone) {

        logger.info("Request : POST /patients/update");

        Patient patientToUpdate = patientService.findPatientByLastNameAndFirstName(family, given);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        Patient patientUpdated = new Patient(family, given, dateOfBirth, sex, address, phone);
        patientUpdated.setId(patientToUpdate.getId());

        patientService.updatePatient(patientUpdated);

        logger.info("Success : patient updated");

        return new ResponseEntity<>(patientUpdated, HttpStatus.OK);
    }

    /**
     * Method managing the GET "/patients/addform" endpoint HTTP request to add a patient using a HTML form.
     *
     * @param patient An empty patient
     * @return The name of the View
     */
    @GetMapping("/patients/addform")
    public String addPatientForm(Patient patient) {

        logger.info("Request : GET /patients/addform");
        logger.info("Success : returning 'patients/addform' view");

        return "patients/addform";
    }

    /**
     * Method managing the POST "/patients/validateform" endpoint HTTP request to add a patient using a HTML form.
     *
     * @param patient The patient to add
     * @param result The BindingResult containing the result of the fields validation
     * @return The name of the View
     */
    @PostMapping("/patients/validateform")
    public String validatePatientForm(@Valid Patient patient, BindingResult result) {

        logger.info("Request : POST /patients/validateform");

        if (!result.hasErrors()) {
            patientService.createPatient(patient);

            logger.info("Success : new patient created, redirect to '/patients/list' view");

            return "redirect:/patients/list";
        }

        logger.error("Error in fields validation : new patient not created, returning '/patients/addform' view");

        return "patients/addform";
    }

    /**
     * Method managing the POST "/patients/add" endpoint HTTP request to add a patient using a command line HTTP client and parameters in the URL request.
     *
     * @param family The last name of the patient
     * @param given The first name of the patient
     * @param dob The date of birth of the patient
     * @param sex The sex of the patient
     * @param address The home address of the patient
     * @param phone The phone number of the patient
     * @return A ResponseEntity containing the location of the patient created and the HTTP status code
     */
    @PostMapping("/patients/add")
    public ResponseEntity<Patient> addPatient(@RequestParam(required = true) String family, @RequestParam(required = true) String given, @RequestParam(required = true) String dob, @RequestParam(required = true) Sex sex, @RequestParam(required = false) String address, @RequestParam(required = false) String phone) {

        logger.info("Request : POST /patients/add");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        Patient patientToAdd = new Patient(family, given, dateOfBirth, sex, address, phone);

        patientService.createPatient(patientToAdd);

        logger.info("Success : new patientToAdd created");

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(patientToAdd.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Method managing the GET "/notes/{lastName}/{firstName}" endpoint HTTP request to redirect to the patient notes view in the Mediscreen Note Microservice
     *
     * @param lastName The last name of the patient
     * @param firstName The first name of the patient
     * @return A ModelAndView containing the redirection information
     */
    @GetMapping("/notes/{lastName}/{firstName}")
    public ModelAndView getPatientNotes(@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName, ModelMap model) {

        logger.info("Request : GET /notes with lastName={} and firstName={}", lastName, firstName);

        logger.info("Success : redirect to '/notes' view in Mediscreen Note Microservice");

        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);

        String redirectedURL = "http://" + noteMicroserviceUrl + "/patHistoryByPatientLastNameAndFirstName";

        return new ModelAndView("redirect:" + redirectedURL, model);
    }

    /**
     * Method managing the GET "/patients/rapport/{id}" endpoint HTTP request to get a patient diabetes risk assessment report given its identifier and in HTML form.
     *
     * @param id The id of the patient
     * @param model The Model containing the rapport generated
     * @return The name of the View
     */
    @GetMapping("/patients/rapport/{id}")
    public String getPatientRapport(@PathVariable("id") Long id, Model model) {

        logger.info("Request : GET /patients/rapport/{}", id);

        Rapport rapport = patientService.getPatientRapport(id);
        model.addAttribute("rapport", rapport);

        logger.info("Success : report for patient with id {} generated, returning '/patients/rapport' view", id);

        return "patients/rapport";
    }
}
