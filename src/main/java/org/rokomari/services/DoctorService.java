package org.rokomari.services;

import org.rokomari.models.Doctor;
import org.rokomari.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Abdullah Al Amin on 7/22/2018.
 */
@Service
public class DoctorService {

    @Autowired
    DoctorRepository repository;

    public boolean insertDoctor(Doctor doctor){
        if (repository.save(doctor) != null)
            return true;
        else
            return false;
    }
}
