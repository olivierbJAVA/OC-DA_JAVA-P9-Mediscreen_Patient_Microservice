package com.mediscreen.patient.controller;

import com.mediscreen.patient.constant.Sex;
import com.mediscreen.patient.domain.Patient;
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
            mockMvc.perform(get("/patients"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("patients", listPatientsToFind))
                    .andExpect(view().name("patients"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findAllPatients();
    }

    @Test
    public void getPatientById_whenIdExist() {
        //ARRANGE
        Patient patientToFind = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToFind.setId(1L);

        doReturn(patientToFind).when(mockPatientService).findPatientById(1L);

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/1"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("patients", patientToFind))
                    .andExpect(view().name("patients"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientById(1L);
    }

    @Test
    public void getPatientById_whenIdNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockPatientService).findPatientById(1L);

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patients/1"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientById(1L);
    }

    @Test
    public void getPatientsByLastName_whenLastNameExist() {
        //ARRANGE
        Patient patientToFind1 = new Patient("PatientTestLastNameToFind", "PatientTestFirstName1", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-111-1111");
        patientToFind1.setId(1);
        Patient patientToFind2 = new Patient("PatientTestLastNameToFind", "PatientTestFirstName2", LocalDate.of(2000,02,02), Sex.F, "PatientTestHomeAddress","222-222-2222");
        patientToFind2.setId(2);

        List<Patient> listPatientsToFind = new ArrayList<>();
        listPatientsToFind.add(patientToFind1);
        listPatientsToFind.add(patientToFind2);

        doReturn(listPatientsToFind).when(mockPatientService).findPatientsByLastName("PatientTestLastNameToFind");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patientsByLastName")
                    .param("lastName","PatientTestLastNameToFind"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("patients", listPatientsToFind))
                    .andExpect(view().name("patients"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientsByLastName("PatientTestLastNameToFind");
    }

    @Test
    public void getPatientsByLastName_whenLastNameNotExist() {
        //ARRANGE
        doThrow(ResourceNotFoundException.class).when(mockPatientService).findPatientsByLastName("PatientTestLastNameNotExist");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patientsByLastName")
                            .param("lastName","PatientTestLastNameNotExist"))
                            .andExpect(status().isNotFound())
                            .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientsByLastName("PatientTestLastNameNotExist");
    }

    @Test
    public void getPatientByLastNameAndFirstName_whenLastNameAndFirstNameExist() {
        //ARRANGE
        Patient patientToFind = new Patient("PatientTestLastName", "PatientTestFirstName", LocalDate.of(2000,01,01), Sex.M, "PatientTestHomeAddress","111-222-3333");
        patientToFind.setId(1L);
        doReturn(patientToFind).when(mockPatientService).findPatientByLastNameAndFirstName("PatientTestLastName", "PatientTestFirstName");

        //ACT & ASSERT
        try {
            mockMvc.perform(get("/patientByLastNameAndFirstName")
                    .param("lastName","PatientTestLastName")
                    .param("firstName","PatientTestFirstName"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("patients", patientToFind))
                    .andExpect(view().name("patients"));
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
            mockMvc.perform(get("/patientByLastNameAndFirstName")
                    .param("lastName","PatientTestLastName")
                    .param("firstName","PatientTestFirstName"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errorResourceNotFound"));
        } catch (Exception e) {
            logger.error("Error in MockMvc", e);
        }

        verify(mockPatientService, times(1)).findPatientByLastNameAndFirstName("PatientTestLastName","PatientTestFirstName");
    }

}
