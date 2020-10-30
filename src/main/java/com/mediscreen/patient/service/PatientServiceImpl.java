package com.mediscreen.patient.service;

import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Class in charge of managing the services for Patient entities.
 */
@Service
@Transactional
public class PatientServiceImpl implements IPatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Return a patient given its id.
     *
     * @param id The id of the patient
     * @return The patient corresponding to the id
     * @throws ResourceNotFoundException if no patient is found for the given id
     */
    @Override
    public Patient findPatientById(Long id) throws ResourceNotFoundException {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Return the list of patients given the last name.
     *
     * @param lastName The last name of patients to return
     * @return The list of patients corresponding to the last name
     * @throws ResourceNotFoundException if no patient is found for the given last name
     */
    @Override
    public List<Patient> findPatientsByLastName(String lastName) throws ResourceNotFoundException {
        List<Patient> listPatients = patientRepository.findByLastName(lastName);

        if( listPatients.isEmpty() ) {
            throw new ResourceNotFoundException(lastName);
        }

        return listPatients;
    }

    /**
     * Return a patient given its last name and first name.
     *
     * @param lastName The last name of the patient
     * @param firstName The first name of the patient
     * @return The patient corresponding to the last name and first name
     * @throws ResourceNotFoundException if no patient is found for the given last name and first name
     */
    @Override
    public Patient findPatientByLastNameAndFirstName(String lastName, String firstName) throws ResourceNotFoundException {
        Patient patient = patientRepository.findByLastNameAndFirstName(lastName, firstName);

        if( patient == null ) {
            throw new ResourceNotFoundException(lastName, firstName);
        }

        return patient;
    }

    /**
     * Return all patients.
     *
     * @return The list of all patients
     */
    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
}
