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
        return repository.save(doctor) != null ? true: false;
    }

    public Doctor getADoctorById(int id){
        return repository.getOne(id);
    }

    public List<Doctor> getAllDoctors(){
        return repository.findAll();
    }

    public boolean updateDoctor(Doctor newDocRecord){
        // If the record contains the id value it will replace the old records with the new record in the id position
        return insertDoctor(newDocRecord);
    }

    public void deleteDoctor(Doctor doctor){
         repository.delete(doctor);
    }
}
