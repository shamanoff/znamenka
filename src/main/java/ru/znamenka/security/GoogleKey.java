package ru.znamenka.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GoogleKey {

    @JsonProperty("client_email")
    private String clientEmail;
}
