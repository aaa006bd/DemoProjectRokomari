package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter@Setter
    @JsonIgnore
    private int id;

    @Getter@Setter
    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Getter@Setter
    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Getter@Setter
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @Getter@Setter
    @Email
    private String email;

    @Getter@Setter
    private String mobile;

    @Getter@Setter
    private String password;

    @Getter@Setter
    @ManyToMany
    @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Role> roles = new HashSet<>();


}
