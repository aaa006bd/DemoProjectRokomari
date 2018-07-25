package org.rokomari.services;

import org.rokomari.models.Appointment;
import org.rokomari.models.Doctor;
import org.rokomari.models.Patient;
import org.rokomari.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Abdullah Al Amin on 7/25/2018.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public boolean insertAppointmentRecord(Appointment appointment){
        return repository.save(appointment) != null?true : false;
    }

    public List<Appointment> findByPatient(Patient p){
        return  repository.findAppointmentsByPatient(p);
    }

    public List<Appointment> seeAllAppointments(Doctor d){
        return repository.findAppointmenstByDoctor(d);
    }

    public List<Appointment> seeAppointmentsOfElderly(Doctor d){
        List<Appointment> appointments = repository.findAppointmenstByDoctor(d);

        List<Appointment> appointmentsElderly = appointments
                                                .stream()
                                                .filter(appointment -> appointment.getPatient().getAge()>=60)
                                                .collect(Collectors.toList());
        return appointmentsElderly;
    }

    @Transactional
    public void cancelAppointmentByPatient(Patient p){
         repository.deleteAppointmentByPatient(p);
    }

    @Transactional
    public void cancelAppointmentByDoctor(Doctor d){
         repository.deleteAppointmentByDoctor(d);
    }

}
