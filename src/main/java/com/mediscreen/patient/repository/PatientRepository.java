package com.mediscreen.patient.repository;

import com.mediscreen.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface extending the JpaRepository interface to manage CRUD methods for Patient entities, using Spring DataJPA.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Return a patient given its last name and first name.
     *
     * @param lastName The last name of the patient
     * @param firstName The first name of the patient
     * @return The patient corresponding to the last name and first name
     */
    Patient findByLastNameAndFirstName (String lastName, String firstName);

}
