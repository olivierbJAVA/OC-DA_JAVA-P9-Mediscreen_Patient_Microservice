package com.mediscreen.patient.service;

import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interface to be implemented to manage the services for Patient entities.
 */
public interface IPatientService {

    /**
     * Return a patient given its id.
     *
     * @param id The id of the patient
     * @return The patient corresponding to the id
     * @throws ResourceNotFoundException if no patient is found for the given id
     */
    Patient findPatientById (Long id) throws ResourceNotFoundException;

    /**
     * Return the list of patients given the last name.
     *
     * @param lastName The last name of patients to return
     * @return The list of patients corresponding to the last name
     * @throws ResourceNotFoundException if no patient is found for the given last name
     */
    List<Patient> findPatientsByLastName(String lastName) throws ResourceNotFoundException;

    /**
     * Return a patient given its last name and first name.
     *
     * @param lastName The last name of the patient
     * @param firstName The first name of the patient
     * @return The patient corresponding to the last name and first name
     * @throws ResourceNotFoundException if no patient is found for the given last name and first name
     */
    Patient findPatientByLastNameAndFirstName (String lastName, String firstName) throws ResourceNotFoundException;

    /**
     * Return all patients.
     *
     * @return The list of all patients
     */
    List<Patient> findAllPatients();

}