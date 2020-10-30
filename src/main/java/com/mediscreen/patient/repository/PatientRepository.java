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

    List<Patient> findByLastName (String lastName);

    Patient findByLastNameAndFirstName (String lastName, String firstName);

}