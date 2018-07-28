package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Abdullah Al Amin on 7/22/2018.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "doctor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter@Setter
    private int id;

    @Getter@Setter
    @NotBlank
    @NotBlank(message = "name is required")
    private String name;

    @Getter@Setter
    @Column(name = "dept")
    @JsonProperty("dept")
    @NotBlank(message = "department is required")
    private String department;

    @Getter@Setter
    @Column(name = "joining")
    @JsonProperty("joining")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "doctor_patient" ,
               joinColumns = @JoinColumn(name = "doctor_id"),
                inverseJoinColumns = @JoinColumn(name = "patient_id"))
    @Getter@Setter
    @JsonIgnore
    private Set<Patient> patients = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        return getId() == doctor.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
