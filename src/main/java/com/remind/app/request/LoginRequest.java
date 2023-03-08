package com.remind.app.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest
(   @NotBlank(message = "Username is not blank")
    String username,

    @NotBlank(message = "Password is not blank")
    String password

 )
{
}
