package org.rokomari.customPayloadsAndMessages;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
public class LoginRequest {

    @Getter@Setter
    private String email;

    @Getter@Setter
    private String password;

}
