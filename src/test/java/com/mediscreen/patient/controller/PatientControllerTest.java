package com.mediscreen.patient.controller;

import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.domain.Patient;
import com.mediscreen.patient.exception.ResourceAlreadyExistException;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.service.IPatientService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class including unit tests for the PatientController Class.
 */
@WebMvcTest(value = PatientController.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(PatientControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatientService mockPatientService;

    @Test
    public void getPatients() {
        //ARRANGE
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

        doReturn(listPatientsToFind).when(mockPatientService).findAllPatients();

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/list"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("patients", listPatientsToFind))
                    .andExpect(view().name("patients/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findAllPatients();
    }

    @Test
    public void getPatientByLastNameAndFirstName_whenLastNameAndFirstNameExist() {
        //ARRANGE
        Patient patientToFind = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToFind.setId(1L);
        doReturn(patientToFind).when(mockPatientService).findPatientByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/patientByFamilyAndGiven")
                    .param("family","PatientTestLastName")
                    .param("given","PatientTestFirstName"))
                    .andExpect(status().isFound());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
        verify(mockPatientService, times(1)).findPatientByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");
    }

    @Test
    public void getPatientByLastNameAndFirstName_whenLastNameAndFirstNameNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockPatientService).findPatientByLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/patientByFamilyAndGiven")
                    .param("family","PatientTestLastName")
                    .param("given","PatientTestFirstName"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientByLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");
    }

    @Test
    public void showUpdatePatientForm() {
        //ARRANGE
        Patient patientTest = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientTest.setId(1L);

        doReturn(patientTest).when(mockPatientService).findPatientById(1L);

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/updateform/1"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("patient", patientTest))
                    .andExpect(view().name("patients/updateform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientById(1L);
    }

    @Test
    public void updatePatientForm_whenNoErrorInFields() {
        //ARRANGE
        Patient patientTest = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientTest.setId(1L);

        doReturn(patientTest).when(mockPatientService).updatePatient(patientTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/updateform/1")
                    .param("id","1")
                    .param("lastName", "PatientTestLastName")
                    .param("firstName", "PatientTestFirstName")
                    .param("dateOfBirth", LocalDate.of(2000,01,01).toString())
                    .param("sex", "M")
                    .param("homeAddress", "PatientTestHomeAddress")
                    .param("phoneNumber", "111-222-3333"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", "/patients/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).updatePatient(any(Patient.class));
    }

    @Test
    public void updatePatientForm_whenErrorInFields() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/updateform/1")
                    .param("id","1")
                    .param("lastName", "PatientTestLastName")
                    .param("firstName", "PatientTestFirstName")
                    // error : mandatory date of birth is missing
                    .param("sex", "M")
                    .param("homeAddress", "PatientTestHomeAddress")
                    .param("phoneNumber", "111-222-3333"))
                    .andExpect(model().attributeHasFieldErrors("patient", "dateOfBirth"))
                    .andExpect(view().name("patients/updateform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, never()).updatePatient(any(Patient.class));
    }

    @Test
    public void addPatientForm() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/addform"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("patients/addform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }
    }

    @Test
    public void validatePatientForm_whenNoErrorInFields() {
        //ARRANGE
        Patient patientTest = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientTest.setId(1L);

        doReturn(patientTest).when(mockPatientService).createPatient(patientTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/validateform")
                    .param("lastName", "PatientTestLastName")
                    .param("firstName", "PatientTestFirstName")
                    .param("dateOfBirth", LocalDate.of(2000,01,01).toString())
                    .param("sex", "M")
                    .param("homeAddress", "PatientTestHomeAddress")
                    .param("phoneNumber", "111-222-3333"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", "/patients/list"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).createPatient(any(Patient.class));
    }

    @Test
    public void validatePatientForm_whenErrorInFields() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/validateform")
                    .param("lastName", "PatientTestLastName")
                    .param("firstName", "PatientTestFirstName")
                    // error : mandatory date of birth is missing
                    .param("sex", "M")
                    .param("homeAddress", "PatientTestHomeAddress")
                    .param("phoneNumber", "111-222-3333"))
                    .andExpect(model().attributeHasFieldErrors("patient", "dateOfBirth"))
                    .andExpect(view().name("patients/addform"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, never()).createPatient(any(Patient.class));
    }

    @Test
    public void updatePatient_whenPatientExistAndNoErrorInFields() {
        //ARRANGE
        Patient patientTest = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientTest.setId(1L);

        doReturn(patientTest).when(mockPatientService).findPatientByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        doReturn(patientTest).when(mockPatientService).updatePatient(patientTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/update")
                    .param("family", "PatientTestLastName")
                    .param("given", "PatientTestFirstName")
                    .param("dob", LocalDate.of(2000,01,01).toString())
                    .param("sex", "M")
                    .param("address", "PatientTestHomeAddress")
                    .param("phone", "111-222-3333"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).updatePatient(any(Patient.class));
    }

    @Test
    public void updatePatient_whenPatientExistAndErrorInFields() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/update")
                    .param("family", "PatientTestLastName")
                    .param("given", "PatientTestFirstName")
                    // error : mandatory date of birth is missing
                    .param("sex", "M")
                    .param("address", "PatientTestHomeAddress")
                    .param("phone", "111-222-3333"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, never()).updatePatient(any(Patient.class));
    }

    @Test
    public void updatePatient_whenPatientNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockPatientService).findPatientByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/update")
                    .param("family", "PatientTestLastName")
                    .param("given", "PatientTestFirstName")
                    .param("dob", LocalDate.of(2000,01,01).toString())
                    .param("sex", "M")
                    .param("address", "PatientTestHomeAddress")
                    .param("phone", "111-222-3333"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, never()).updatePatient(any(Patient.class));
    }

    @Test
    public void addPatient_whenPatientNotAlreadyExistAndNoErrorInFields() {
        //ARRANGE
        Patient patientTest = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");

        doReturn(patientTest).when(mockPatientService).createPatient(patientTest);

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/add")
                    .param("family", "PatientTestLastName")
                    .param("given", "PatientTestFirstName")
                    .param("dob", LocalDate.of(2000,01,01).toString())
                    .param("sex", "M")
                    .param("address", "PatientTestHomeAddress")
                    .param("phone", "111-222-3333"))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).createPatient(any(Patient.class));
    }

    @Test
    public void addPatient_whenPatientNotAlreadyExistAndErrorInFields() {
        //ARRANGE

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/update")
                    .param("family", "PatientTestLastName")
                    .param("given", "PatientTestFirstName")
                    // error : mandatory date of birth is missing
                    .param("sex", "M")
                    .param("address", "PatientTestHomeAddress")
                    .param("phone", "111-222-3333"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, never()).createPatient(any(Patient.class));
    }

    @Test
    public void addPatient_whenPatientAlreadyExist() {
        //ARRANGE
        Patient patientTest = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");

        doThrow(ResourceAlreadyExistException.class).when(mockPatientService).createPatient(any(Patient.class));

        //ACT & ASSERT
        try {
            mockMvc.perform(post("/patients/add")
                    .param("family", "PatientTestLastName")
                    .param("given", "PatientTestFirstName")
                    .param("dob", LocalDate.of(2000,01,01).toString())
                    .param("sex", "M")
                    .param("address", "PatientTestHomeAddress")
                    .param("phone", "111-222-3333"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, never()).updatePatient(any(Patient.class));
    }
}
