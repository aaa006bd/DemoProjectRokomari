package org.rokomari.services;

import org.rokomari.models.Appointment;
import org.rokomari.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
