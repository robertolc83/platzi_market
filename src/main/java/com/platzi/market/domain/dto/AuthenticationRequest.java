package com.platzi.market.domain.dto;

/**
 * Descrición: Clase creada para Jason Web Token
 */
public class AuthenticationRequest {

    private String usermane;
    private String password;

    public String getUsermane() {
        return usermane;
    }

    public void setUsermane(String usermane) {
        this.usermane = usermane;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
