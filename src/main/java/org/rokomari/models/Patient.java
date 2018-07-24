package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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


    @ManyToMany(mappedBy = "patients")
    @Getter@Setter
    private Set<Doctor> doctors = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return getId() == patient.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
