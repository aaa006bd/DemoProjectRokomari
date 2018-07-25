package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Abdullah Al Amin on 7/25/2018.
 */
@Entity
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter@Setter
    private int id;

    @Getter@Setter
    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Getter@Setter
    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Getter@Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime appointment;

}
