package com.remind.app.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest (
        @NotBlank(message = "Username is not blank")
        /*@Min(value = 6, message = "Username must be larger 6 character")
        @Max(value = 32, message = "Username must be smaller 32 character")*/
        String clientName,

        @NotBlank(message = "Password is not blank")
        /*@Min(value = 8, message = "Password must be larger 8 character")
        @Max(value = 32, message = "Password must be smaller 32 character")*/
        String password,

        @NotBlank(message = "Your name is not blank")
        String fullName



)
{
}
