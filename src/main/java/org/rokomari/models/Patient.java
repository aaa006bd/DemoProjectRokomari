package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Patient {

    @Id
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
    @JsonProperty("symptom_summary")
    private String symptomSummary;
}
