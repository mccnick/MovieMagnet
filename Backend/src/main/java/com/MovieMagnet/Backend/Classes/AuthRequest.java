package com.MovieMagnet.Backend.Classes;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequest {

    @Schema(description = "Email address of the user")
    private String email;

    @Schema(description = "Password attempt")
    private String password;

    public AuthRequest(){

    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

}
