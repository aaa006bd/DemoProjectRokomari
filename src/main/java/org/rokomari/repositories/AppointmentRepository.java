package org.rokomari.repositories;

import org.rokomari.models.Appointment;
import org.rokomari.models.Doctor;
import org.rokomari.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Abdullah Al Amin on 7/25/2018.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    Appointment findAppointmentByPatientAndDoctor(Patient p,Doctor d);
    List<Appointment>findAppointmentsByPatient(Patient p);
    List<Appointment> findAppointmentsByDoctor(Doctor d);
    Appointment findAppointmentByPatient(Patient p);
    Appointment findAppointmentByDoctor(Doctor d);
    void deleteAppointmentByPatient(Patient p);
    void deleteAppointmentByDoctor(Doctor d);

}
