package org.rokomari.customPayloadsAndMessages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

/**
 * Created by Abdullah Al Amin on 7/28/2018.
 */
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"status","first_name","email"})
public class LoginResponse {

    @Getter@Setter
    @JsonProperty("first_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @Getter@Setter
    @Email
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @Getter@Setter
    private String status;
}
