package org.rokomari.repositories;

import org.rokomari.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
