package BTEC.ASM.project.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "User code must not be blank")
        @Size(max = 50, message = "User code must not exceed 50 characters")
        String userCode,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 6, max = 100, message = "Password length must be between 6 and 100 characters")
        String password
) {}
