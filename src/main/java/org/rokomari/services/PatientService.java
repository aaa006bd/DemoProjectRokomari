package org.rokomari.services;

import org.rokomari.models.Patient;
import org.rokomari.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */
@Service
public class PatientService {
    @Autowired
    PatientRepository repository;

    public boolean insertPatient(Patient patient){
        return repository.save(patient) != null? true: false;
    }

    public Patient getAPatientById(int id){
        return repository.getOne(id);
    }

    public List<Patient> getAllPatients(){
        return repository.findAll();
    }

    public boolean updatePatient(Patient newPatientRec){
        // If the record contains the id value it will replace the old records with the new record in the id position
        return insertPatient(newPatientRec);
    }

    public void deletePatient(Patient patient){
        repository.delete(patient);
    }
}
