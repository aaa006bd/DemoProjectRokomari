package org.rokomari.repositories;

import org.rokomari.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abdullah Al Amin on 7/25/2018.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
