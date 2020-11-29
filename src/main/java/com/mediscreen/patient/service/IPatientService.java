package com.mediscreen.patient.service;

import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Rapport;
import com.mediscreen.patient.exception.ResourceAlreadyExistException;
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
    Patient findPatientById(Long id) throws ResourceNotFoundException;

    /**
     * Return a patient given its last name and first name.
     *
     * @param lastName The last name of the patient
     * @param firstName The first name of the patient
     * @return The patient corresponding to the last name and first name
     * @throws ResourceNotFoundException if no patient is found for the given last name and first name
     */
    Patient findPatientByLastNameAndFirstName(String lastName, String firstName) throws ResourceNotFoundException;

    /**
     * Return all patients.
     *
     * @return The list of all patients
     */
    List<Patient> findAllPatients();

    /**
     * Update a patient.
     *
     * @param patient The patient to update
     * @return The patient updated
     * @throws ResourceNotFoundException if the patient to update does not exist
     * @throws ResourceAlreadyExistException if a patient with the same last name and first name already exist
     */
    Patient updatePatient(Patient patient) throws ResourceNotFoundException, ResourceAlreadyExistException;

    /**
     * Create a patient.
     *
     * @param patient The patient to create
     * @return The patient created
     * @throws ResourceAlreadyExistException if a patient with the same last name and first name already exist
     */
    Patient createPatient(Patient patient) throws ResourceAlreadyExistException;

    /**
     * Get the patient diabetes risk assessment report.
     *
     * @param id The patient identifier
     * @return The rapport generated
     */
    Rapport getPatientRapport(long id);
}
