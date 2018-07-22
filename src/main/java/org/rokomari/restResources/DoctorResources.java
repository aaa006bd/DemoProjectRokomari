package org.rokomari.restResources;

import org.rokomari.models.Doctor;
import org.rokomari.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Abdullah Al Amin on 7/22/2018.
 */

@RestController
public class DoctorResources {

    @Autowired
    private DoctorService service;

    @PostMapping("api/insert/doctor/new")
    public void insertDoctor(@RequestBody Doctor doctor){
        service.insertDoctor(doctor);
    }

    @GetMapping(value = "api/doctors", headers = "doctor_id")
    public@ResponseBody Doctor getADoctor(@RequestHeader("doctor_id") int docId){
        return service.getADoctorById(docId);
    }

    @GetMapping(value ="api/doctors")
    public List<Doctor> getAllDoctors(){
        return service.getAllDoctors();
    }


}
