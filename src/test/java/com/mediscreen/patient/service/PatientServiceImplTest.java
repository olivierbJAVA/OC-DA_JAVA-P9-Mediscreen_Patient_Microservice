package com.mediscreen.patient.service;

import com.mediscreen.patient.constant.Assessment;
import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.domain.Rapport;
import com.mediscreen.patient.exception.ResourceAlreadyExistException;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.proxy.RapportMicroserviceProxy;
import com.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Class including unit tests for the PatientServiceImpl Class.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientServiceImplUnderTest;

    @Mock
    private PatientRepository mockPatientRepository;

    @Mock
    private RapportMicroserviceProxy mockRapportProxy;

    @Test
    public void findPatientById_whenIdExist() {
        // ARRANGE
        Patient patientToFind = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToFind.setId(1L);
        doReturn(Optional.of(patientToFind)).when(mockPatientRepository).findById(patientToFind.getId());

        // ACT
        Patient patientFound = patientServiceImplUnderTest.findPatientById(1L);

        // ASSERT
        verify(mockPatientRepository, times(1)).findById(1L);
        assertEquals(patientToFind, patientFound);
    }

    @Test
    public void findPatientById_whenIdNotExist() {
        // ARRANGE
        doReturn(Optional.empty()).when(mockPatientRepository).findById(1L);

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            patientServiceImplUnderTest.findPatientById(1L);
        });
        verify(mockPatientRepository, times(1)).findById(1L);
    }

    @Test
    public void findPatientByLastNameAndFirstName_whenLastNameAndFirstNameExist() {
        // ARRANGE
        Patient patientToFind = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToFind.setId(1L);
        doReturn(patientToFind).when(mockPatientRepository).findByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        // ACT
        Patient patientFound = patientServiceImplUnderTest.findPatientByLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");

        // ASSERT
        verify(mockPatientRepository, times(1)).findByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");
        assertEquals(patientToFind, patientFound);
    }

    @Test
    public void findPatientByLastNameAndFirstName_whenLastNameAndFirstNameNotExist() {
        // ARRANGE
        doReturn(null).when(mockPatientRepository).findByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            patientServiceImplUnderTest.findPatientByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");
        });
        verify(mockPatientRepository, times(1)).findByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");
    }

    @Test
    public void findAllPatients() {
        // ARRANGE
        Patient patientToFind1 = new Patient("PatientTestLastName1", "PatientTestFirstName1", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress1","111-111-1111");
        patientToFind1.setId(1);
        Patient patientToFind2 = new Patient("PatientTestLastName2", "PatientTestFirstName2", LocalDate.of(2000,02,02), Sex.F, "PatientTestHomeAddress2","222-222-2222");
        patientToFind2.setId(2);
        Patient patientToFind3 = new Patient("PatientTestLastName3", "PatientTestFirstName3", LocalDate.of(2000,03,03), Sex.M, "PatientTestHomeAddress3","333-333-3333");
        patientToFind3.setId(3);

        List<Patient> listPatientsToFind = new ArrayList<>();
        listPatientsToFind.add(patientToFind1);
        listPatientsToFind.add(patientToFind2);
        listPatientsToFind.add(patientToFind3);

        doReturn(listPatientsToFind).when(mockPatientRepository).findAll();

        // ACT
        List<Patient> listPatientsFound = patientServiceImplUnderTest.findAllPatients();

        // ASSERT
        verify(mockPatientRepository, times(1)).findAll();
        assertEquals(listPatientsToFind, listPatientsFound);
    }

    @Test
    public void updatePatient_whenIdExistAndPatientWithSameNameAndFirstNameNotAlreadyExist() {
        // ARRANGE
        Patient patientToUpdate = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToUpdate.setId(1L);
        doReturn(Optional.of(patientToUpdate)).when(mockPatientRepository).findById(1L);
        doReturn(null).when(mockPatientRepository).findByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");
        doReturn(patientToUpdate).when(mockPatientRepository).save(patientToUpdate);

        // ACT
        Patient patientUpdated = patientServiceImplUnderTest.updatePatient(patientToUpdate);

        // ASSERT
        verify(mockPatientRepository, times(1)).save(patientToUpdate);
        assertEquals(patientToUpdate, patientUpdated);
    }

    @Test
    public void updatePatient_whenIdExistAndPatientWithSameNameAndFirstNameAlreadyExist() {
        // ARRANGE
        Patient patientAlreadyExisting = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2013,01,01), Sex.M, "PatientExistingHomeAddress","123-123-1234");
        patientAlreadyExisting.setId(1L);
        Patient patientToUpdate = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToUpdate.setId(2L);
        doReturn(Optional.of(patientToUpdate)).when(mockPatientRepository).findById(2L);
        doReturn(patientAlreadyExisting).when(mockPatientRepository).findByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        // ACT & ASSERT
        assertThrows(ResourceAlreadyExistException.class, () -> {
            patientServiceImplUnderTest.updatePatient(patientToUpdate);
        });
        verify(mockPatientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void updatePatient_whenIdNotExist() {
        // ARRANGE
        doReturn(Optional.empty()).when(mockPatientRepository).findById(1L);

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            patientServiceImplUnderTest.findPatientById(1L);
        });
        verify(mockPatientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void createPatient_whenPatientWithSameNameAndFirstNameNotAlreadyExist() {
        // ARRANGE
        Patient patientToCreate = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToCreate.setId(1L);
        doReturn(null).when(mockPatientRepository).findByLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");
        doReturn(patientToCreate).when(mockPatientRepository).save(patientToCreate);

        // ACT
        Patient patientCreated = patientServiceImplUnderTest.createPatient(patientToCreate);

        // ASSERT
        verify(mockPatientRepository, times(1)).save(patientToCreate);
        assertEquals(patientToCreate, patientCreated);
    }

    @Test
    public void createPatient_whenPatientWithSameNameAndFirstNameAlreadyExist() {
        // ARRANGE
        Patient patientToCreate = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToCreate.setId(1L);
        doReturn(patientToCreate).when(mockPatientRepository).findByLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");

        // ACT & ASSERT
        assertThrows(ResourceAlreadyExistException.class, () -> {
            patientServiceImplUnderTest.createPatient(patientToCreate);
        });
        verify(mockPatientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void getPatientRapport() {
        // ARRANGE
        Rapport rapportToGet = new Rapport( "PatientTestLastName", "PatientTestFirstName", Sex.M, ChronoUnit.YEARS.between(LocalDate.of(2000,01,01), LocalDate.now()), Assessment.None);
        doReturn(rapportToGet).when(mockRapportProxy).getPatientRapportById(1L);

        // ACT
        Rapport rapportGet = patientServiceImplUnderTest.getPatientRapport(1L);

        // ASSERT
        verify(mockRapportProxy, times(1)).getPatientRapportById(1L);
        assertEquals(rapportToGet, rapportGet);
    }
}
