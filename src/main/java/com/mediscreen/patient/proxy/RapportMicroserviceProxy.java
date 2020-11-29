package com.mediscreen.patient.proxy;


import com.mediscreen.patient.domain.Rapport;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Proxy for the Rapport Microservice using Feign to communicate with the Rapport Microservice.
 */
@FeignClient(name = "RAPPORT-MICROSERVICE", url = "localhost:8080")
public interface RapportMicroserviceProxy {

    /**
     * Get the patient diabetes risk assessment report.
     *
     * @param id The id of the patient
     * @return The rapport
     */
    @GetMapping(value = "/assess/id")
    Rapport getPatientRapportById(@RequestParam("id") long id);

}
