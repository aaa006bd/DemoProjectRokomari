package org.rokomari.restResources;

import org.rokomari.models.Appointment;
import org.rokomari.models.Doctor;
import org.rokomari.models.Patient;
import org.rokomari.services.AppointmentService;
import org.rokomari.services.DoctorService;
import org.rokomari.services.PatientService;
import org.rokomari.customPayloadsAndMessages.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */

@RestController
public class AssignResources {

    @Autowired
    PatientService patientService;
    @Autowired
    DoctorService doctorService;

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/api" +
            "/doctor/{docId}/patient/{patId}")
    public ResponseEntity<StatusMessage> assignDoctor(@PathVariable("docId") int docId,
                                                      @PathVariable("patId") int patId){
        try{

            Patient patient = patientService.getAPatientById(patId);
            Doctor doctor = doctorService.getADoctorById(docId);

            patient.getDoctors().add(doctor);
            doctor.getPatients().add(patient);

            patient.setId(patId);
            doctor.setId(docId);

            patientService.updatePatient(patient);
            doctorService.updateDoctor(doctor);

            return ResponseEntity
                    .ok()
                    .body(new StatusMessage(String.format("Doctor %s has been assigned to patient %s"
                            ,doctor.getName(),patient.getName())));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("could not find doctor or patient record"));
        }

    }

    @GetMapping("api/doctors/{docId}/patients")
    public Set<Patient> getAllPatientUnderDoctor(@PathVariable("docId") int docId){
        Doctor doctor = doctorService.getADoctorById(docId);
        return doctor.getPatients();
    }

    @PostMapping("api/appointment/doctor/patient/{appointment_time}")
    public ResponseEntity<StatusMessage> makeAnAppointment(@RequestHeader("doctor_id")int docId,
                                                           @RequestHeader("patient_id")int patId,
                                                           @PathVariable("appointment_time")
                                                               @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime time){
        try{

            Patient patient = patientService.getAPatientById(patId);
            Doctor doctor = doctorService.getADoctorById(docId);

            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setAppointment(time);

            appointmentService.insertAppointmentRecord(appointment);

            return ResponseEntity
                    .ok()
                    .body(new StatusMessage(String.format("Doctor %s has been assigned to patient %s"
                            ,doctor.getName(),patient.getName())));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("could not find doctor or patient record"));
        }
    }

    @GetMapping("api/appointment/patient/{id}")
    public List<Appointment> getAppointment(@PathVariable("id")int id){
        return appointmentService.findByPatient(patientService.getAPatientById(id));
    }

    @DeleteMapping("api/appointment/delete/patient/{id}")
    public ResponseEntity<StatusMessage> cancelAppointmentByPatient(@PathVariable("id") int id){
        try {
            Patient patient = patientService.getAPatientById(id);
            appointmentService.cancelAppointmentByPatient(patient);

            return ResponseEntity
                    .ok(new StatusMessage("appointment cancelled by patient: "+patient.getName()));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("could not find the appointment or patient"));
        }
    }
    @DeleteMapping("api/appointment/delete/doctor/{id}")
    public ResponseEntity<StatusMessage> cancelAppointmentByDoctor(@PathVariable("id") int id){
        try {
            Doctor doctor = doctorService.getADoctorById(id);
            appointmentService.cancelAppointmentByDoctor(doctor);

            return ResponseEntity
                    .ok(new StatusMessage("appointment cancelled by doctor: "+doctor.getName()));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("could not find the appointment or doctor"));
        }
    }
}
