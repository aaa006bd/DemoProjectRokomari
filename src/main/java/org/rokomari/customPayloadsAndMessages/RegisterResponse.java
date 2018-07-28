package org.rokomari.customPayloadsAndMessages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rokomari.models.Role;
import org.rokomari.models.User;

import javax.validation.constraints.Email;
import java.util.Set;

/**
 * Created by Abdullah Al Amin on 7/28/2018.
 */
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"first_name","last_name","email","mobile","status"})
public class RegisterResponse {

    @Getter@Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("first_name")
    private String firstName;

    @Getter@Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("last_name")
    private String lastName;

    @Getter@Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @Getter@Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobile;

    @Getter@Setter
    private String status;


}
