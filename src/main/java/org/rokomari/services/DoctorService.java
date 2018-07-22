package org.rokomari.services;

import org.rokomari.models.Doctor;
import org.rokomari.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Doctor getADoctorById(int id){
        return repository.getOne(id);
    }

    public List<Doctor> getAllDoctors(){
        return repository.findAll();
    }
}
