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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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

    @GetMapping("api/patients/{patId}/doctors")
    public Set<Doctor> getAllDoctorsVisitedByPatient(@PathVariable("patId") int patId){
        Patient patient = patientService.getAPatientById(patId);
        return patient.getDoctors();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("api/appointment/update/{appointment_time}")
    public ResponseEntity<StatusMessage> UpdateAppointment(@RequestHeader("doctor_id")int docId,
                                                           @RequestHeader("patient_id")int patId,
                                                           @PathVariable("appointment_time")
                                                           String updatedTime){
        try{

            Patient patient = patientService.getAPatientById(patId);
            Doctor doctor = doctorService.getADoctorById(docId);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime time = LocalDateTime.parse(updatedTime,format);


            appointmentService.updateAppointment(doctor, patient, time);


            return ResponseEntity
                    .ok()
                    .body(new StatusMessage("time has been updated. new Appointment time: "
                            +time.toLocalDate()+" "+time.toLocalTime()));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusMessage("could not update appointment record"));
        }
    }

    @GetMapping("api/appointment/patient/{id}")
    public List<Appointment> getAppointment(@PathVariable("id")int id){
        return appointmentService.findByPatient(patientService.getAPatientById(id));
    }



    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
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
