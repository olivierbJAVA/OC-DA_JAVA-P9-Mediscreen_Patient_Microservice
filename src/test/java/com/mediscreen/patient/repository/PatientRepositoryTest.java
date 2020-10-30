package com.mediscreen.patient.repository;

import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Class including tests for the Patient entity Repository.
 */
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql({"/cleandb-test.sql","/data-test.sql"})
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepositoryUnderTest;

    @Sql({"/cleandb-test.sql","/data-test.sql"})
    @BeforeEach
    public void beforeEachTest(){
    }

    @Test
    public void findPatientById() {
        // ACT
        Optional<Patient> patient = patientRepositoryUnderTest.findById(1L);

        // ASSERT
        assertTrue(patient.isPresent());
        assertEquals("Ferguson", patient.get().getLastName());
        assertEquals("Lucas", patient.get().getFirstName());
        assertEquals(LocalDate.of( 1968,6,22), patient.get().getDateOfBirth());
        assertEquals(Sex.M, patient.get().getSex());
        assertEquals("2 Warren Street", patient.get().getHomeAddress());
        assertEquals("387-866-1399", patient.get().getPhoneNumber());
    }

    @Test
    public void findPatientByLastNameWhenOnePatientIsPresent() {
        // ACT
        List<Patient> listPatients = patientRepositoryUnderTest.findByLastName("Ferguson");

        // ASSERT
        assertEquals(1, listPatients.size());
        assertEquals("Ferguson", listPatients.get(0).getLastName());
        assertEquals("Lucas", listPatients.get(0).getFirstName());
        assertEquals(LocalDate.of( 1968,6,22), listPatients.get(0).getDateOfBirth());
        assertEquals(Sex.M, listPatients.get(0).getSex());
        assertEquals("2 Warren Street", listPatients.get(0).getHomeAddress());
        assertEquals("387-866-1399", listPatients.get(0).getPhoneNumber());
    }

    @Test
    public void findPatientByLastNameWhenThreePatientsArePresent() {
        // ACT
        List<Patient> listPatients = patientRepositoryUnderTest.findByLastName("Rees");

        // ASSERT
        assertEquals(3, listPatients.size());
    }

    @Test
    public void findPatientByLastNameAndFirstName() {
        // ACT
        Patient patient = patientRepositoryUnderTest.findByLastNameAndFirstName("Ferguson", "Lucas");

        // ASSERT
        assertNotNull(patient);
        assertEquals("Ferguson", patient.getLastName());
        assertEquals("Lucas", patient.getFirstName());
        assertEquals(LocalDate.of( 1968,6,22), patient.getDateOfBirth());
        assertEquals(Sex.M, patient.getSex());
        assertEquals("2 Warren Street", patient.getHomeAddress());
        assertEquals("387-866-1399", patient.getPhoneNumber());
    }

    @Test
    public void findAllPatients() {
        // ACT
        List<Patient> listPatients = patientRepositoryUnderTest.findAll();

        // ASSERT
        assertEquals(12, listPatients.size());
    }
}
