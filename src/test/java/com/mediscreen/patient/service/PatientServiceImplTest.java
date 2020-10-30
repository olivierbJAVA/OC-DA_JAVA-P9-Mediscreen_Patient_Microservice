package com.mediscreen.patient.service;

import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Class including unit tests for the PatientServiceImpl Class.
 */
@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientServiceImplUnderTest;

    @Mock
    private PatientRepository mockPatientRepository;

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
    public void findPatientsByLastName_whenLastNameExist() {
        // ARRANGE
        Patient patientToFind1 = new Patient("PatientTestLastNameToFind", "PatientTestFirstName1", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-111-1111");
        patientToFind1.setId(1);
        Patient patientToFind2 = new Patient("PatientTestLastNameToFind", "PatientTestFirstName2", LocalDate.of(2000,02,02), Sex.F, "PatientTestHomeAddress","222-222-2222");
        patientToFind2.setId(2);

        List<Patient> listPatientsToFind = new ArrayList<>();
        listPatientsToFind.add(patientToFind1);
        listPatientsToFind.add(patientToFind2);

        doReturn(listPatientsToFind).when(mockPatientRepository).findByLastName("PatientTestLastNameToFind");

        // ACT
        List<Patient> listPatientsFound = patientServiceImplUnderTest.findPatientsByLastName("PatientTestLastNameToFind");

        // ASSERT
        verify(mockPatientRepository, times(1)).findByLastName("PatientTestLastNameToFind");
        assertEquals(listPatientsToFind, listPatientsFound);
    }

    @Test
    public void findPatientsByLastName_whenLastNameNotExist() {
        // ARRANGE
        List<Patient> listPatientsEmpty = new ArrayList<>();
        doReturn(listPatientsEmpty).when(mockPatientRepository).findByLastName("PatientTestLastNameNotExist");

        // ACT & ASSERT
        assertThrows(ResourceNotFoundException.class, () -> {
            patientServiceImplUnderTest.findPatientsByLastName("PatientTestLastNameNotExist");
        });
        verify(mockPatientRepository, times(1)).findByLastName("PatientTestLastNameNotExist");
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
}
