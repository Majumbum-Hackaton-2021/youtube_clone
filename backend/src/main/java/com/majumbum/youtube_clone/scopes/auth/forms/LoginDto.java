package com.majumbum.youtube_clone.scopes.auth.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDto {
    @NotBlank
    @Size(max = 200)
    public String email;

    @NotBlank
    @Size(max = 200)
    public String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
