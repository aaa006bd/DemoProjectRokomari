package org.rokomari.repositories;

import org.rokomari.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abdullah Al Amin on 7/22/2018.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
