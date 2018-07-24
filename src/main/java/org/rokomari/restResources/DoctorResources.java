package org.rokomari.restResources;

import org.rokomari.models.Doctor;
import org.rokomari.models.Patient;
import org.rokomari.services.DoctorService;
import org.rokomari.statusCustom.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Created by Abdullah Al Amin on 7/22/2018.
 */

@RestController
public class DoctorResources {

    @Autowired
    private DoctorService service;

    @PostMapping("api/insert/doctor/new")
    public ResponseEntity<StatusMessage> insertDoctor(@RequestBody Doctor doctor){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doctor.getId()).toUri();

        if(service.insertDoctor(doctor)){
            return ResponseEntity
                    .created(location)
                    .body(new StatusMessage("success"));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusMessage("could not create"));

    }

    @GetMapping(value = "api/doctors", headers = "doctor_id")
    public@ResponseBody Doctor getADoctor(@RequestHeader("doctor_id") int docId){
        return service.getADoctorById(docId);
    }

    @GetMapping(value ="api/doctors")
    public List<Doctor> getAllDoctors(){
        return service.getAllDoctors();
    }

    @GetMapping("api/doctors/{docId}/patients")
    public Set<Patient> getAllPatientUnderDoctor(@PathVariable("docId") int docId){
        Doctor doctor = service.getADoctorById(docId);
        return doctor.getPatients();
    }

    @PutMapping("/api/update/doctors")
    public ResponseEntity<StatusMessage> updateDoctor(@RequestBody Doctor newDocRec,
                                                      @RequestHeader("doctor_id") int docId){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDocRec.getId()).toUri();

        try {
            if(service.getADoctorById(docId) != null){// this line will throw exception
                newDocRec.setId(docId);
                newDocRec.setPatients(service.getADoctorById(docId).getPatients());
                service.updateDoctor(newDocRec);
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

    @DeleteMapping("/api/delete/doctors")
    public ResponseEntity<StatusMessage> deleteDoctor(@RequestHeader("doctor_id") int docId){
        try {
            Doctor docRecord = service.getADoctorById(docId);
            service.deleteDoctor(docRecord);
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
