package org.rokomari.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {

    @Getter@Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter@Setter
    private String name;

    @Getter@Setter
    private int age;

    @Getter@Setter
    private String mobile;

    @Getter@Setter
    private String gender;

    @Getter@Setter
    private String occupation;

    @Getter@Setter
    @Column(name = "symptom_summary")
    private String symptomSummary;
}
