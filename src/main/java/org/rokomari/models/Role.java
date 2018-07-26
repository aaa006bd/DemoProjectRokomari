package org.rokomari.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter@Setter
    private int id;


    @Enumerated(EnumType.STRING)
    @Getter@Setter
    private RoleName roleName;
}
