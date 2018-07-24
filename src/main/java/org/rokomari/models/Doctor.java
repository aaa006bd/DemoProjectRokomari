package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private String name;

    @Getter@Setter
    @Column(name = "dept")
    @JsonProperty("dept")
    private String department;

    @Getter@Setter
    @Column(name = "joining")
    @JsonProperty("joining")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfJoining;
}
