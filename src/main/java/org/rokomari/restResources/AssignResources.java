package org.rokomari.restResources;

import org.rokomari.models.Doctor;
import org.rokomari.models.Patient;
import org.rokomari.repositories.PatientRepository;
import org.rokomari.services.DoctorService;
import org.rokomari.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */

@RestController
public class AssignResources {

    @Autowired
    PatientService patientService;
    @Autowired
    DoctorService doctorService;

    @PostMapping("/api/doctor/{docId}/patient/{patId}")
    public void assignDoctor(@PathVariable("docId") int docId,
                             @PathVariable("patId") int patId){
        Patient patient = patientService.getAPatientById(patId);
        Doctor doctor = doctorService.getADoctorById(docId);

        patient.getDoctors().add(doctor);
        doctor.getPatients().add(patient);

        patient.setId(patId);
        doctor.setId(docId);

        patientService.updatePatient(patient);
        doctorService.updateDoctor(doctor);

    }
}
