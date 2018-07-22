package org.rokomari.restResources;

import org.rokomari.models.Doctor;
import org.rokomari.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
