package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by Abdullah Al Amin on 7/25/2018.
 */
@Entity
@Table(name = "appointment")
public class Appointment {

    @Getter@Setter
    @Column(name = "doctor_id")
    private int doctorID;

    @Getter@Setter
    @Column(name = "patient_id")
    private int patientID;

    @Getter@Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime appointment;

}
