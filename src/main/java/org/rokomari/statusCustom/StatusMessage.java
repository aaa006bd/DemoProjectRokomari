package org.rokomari.statusCustom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Abdullah Al Amin on 7/24/2018.
 */
@AllArgsConstructor
public class StatusMessage {
    @Getter
    @Setter
    @JsonProperty("status")
    private String message;


}
