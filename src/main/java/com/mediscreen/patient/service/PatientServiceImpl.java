package com.mediscreen.patient.service;

import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Rapport;
import com.mediscreen.patient.exception.ResourceAlreadyExistException;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.proxy.RapportMicroserviceProxy;
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

    private RapportMicroserviceProxy rapportProxy;

    public PatientServiceImpl(PatientRepository patientRepository, RapportMicroserviceProxy rapportProxy) {
        this.patientRepository = patientRepository;
        this.rapportProxy = rapportProxy;
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

        if(patient==null) {
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

    /**
     * Update a patient.
     *
     * @param patient The patient to update
     * @return The patient updated
     * @throws ResourceNotFoundException if the patient to update does not exist
     * @throws ResourceAlreadyExistException if a patient with the same last name and first name already exist
     */
    @Override
    public Patient updatePatient(Patient patient) throws ResourceNotFoundException, ResourceAlreadyExistException {

        patientRepository.findById(patient.getId()).orElseThrow(() -> new ResourceNotFoundException(patient.getId()));

        // Only one patient with last name and first name must exist. So if a patient with the last name and first name already exist we do not update.
        Patient patientAlreadyExist = patientRepository.findByLastNameAndFirstName(patient.getLastName(), patient.getFirstName());
        if(patientAlreadyExist!=null && patientAlreadyExist.getId()!=patient.getId()) {
            throw new ResourceAlreadyExistException(patient.getLastName(), patient.getFirstName());
        }

        return patientRepository.save(patient);
    }

    /**
     * Create a patient.
     *
     * @param patient The patient to create
     * @return The patient created
     * @throws ResourceAlreadyExistException if a patient with the same last name and first name already exist
     */
    @Override
    public Patient createPatient(Patient patient) throws ResourceAlreadyExistException {

        // Only one patient with last name and first name must exit. So if a patient with the last name and first name already exist we do not create a new one.
        Patient patientAlreadyExist = patientRepository.findByLastNameAndFirstName(patient.getLastName(), patient.getFirstName());
        if(patientAlreadyExist!=null) {
            throw new ResourceAlreadyExistException(patient.getLastName(), patient.getFirstName());
        }

        return patientRepository.save(patient);
    }

    /**
     * Return the patient diabetes risk assessment report.
     *
     * @param id The id of the patient
     * @return The rapport
     */
    @Override
    public Rapport getPatientRapport(long id){

        return rapportProxy.getPatientRapportById(id);

    }
}
