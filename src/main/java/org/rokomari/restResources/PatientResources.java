package org.rokomari.restResources;

import org.rokomari.models.Patient;
import org.rokomari.services.PatientService;
import org.rokomari.customPayloadsAndMessages.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */
@RestController
public class PatientResources {
    @Autowired
    private PatientService service;

    @PostMapping("api/insert/patient/new")
    public ResponseEntity<StatusMessage> insertPatient(@RequestBody Patient patient){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patient.getId()).toUri();

        if(service.insertPatient(patient)){
            return ResponseEntity
                    .created(location)
                    .body(new StatusMessage("success"));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusMessage("could not create"));

    }

    @GetMapping(value = "api/patients", headers = "patient_id")
    public@ResponseBody
    Patient getAPatient(@RequestHeader("patient_id") int patId){
        return service.getAPatientById(patId);
    }

    @GetMapping(value ="api/patients")
    public List<Patient> getAllDoctors(){
        return service.getAllPatients();
    }

    @PutMapping("/api/update/patients")
    public ResponseEntity<StatusMessage> updatePatient(@RequestBody Patient newPatRec,
                                                      @RequestHeader("patient_id") int patId){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPatRec.getId()).toUri();

        try {
            if(service.getAPatientById(patId) != null){// this line will throw exception
                newPatRec.setId(patId);
                service.updatePatient(newPatRec);
            }
            return ResponseEntity
                    .created(location)
                    .body(new StatusMessage("updated"));

        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("Record for update cannot be found"));
        }
    }

    @DeleteMapping("/api/delete/patients")
    public ResponseEntity<StatusMessage> deletePatient(@RequestHeader("patient_id") int patId){
        try {
            Patient patRecord = service.getAPatientById(patId);
            service.deletePatient(patRecord);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new StatusMessage("deleted"));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("cannot find the record to delete"));
        }


    }


}
