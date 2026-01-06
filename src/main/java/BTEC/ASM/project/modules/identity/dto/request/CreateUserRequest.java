package BTEC.ASM.project.modules.identity.dto.request;

import jakarta.validation.constraints.*;
import java.util.Set;

public record CreateUserRequest(

        @NotBlank(message = "User code must not be blank")
        @Size(max = 50, message = "User code must not exceed 50 characters")
        String userCode,

        @NotBlank(message = "Full name must not be blank")
        @Size(max = 255, message = "Full name must not exceed 255 characters")
        String fullName,

        @Email(message = "Email must be a valid email address")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @NotBlank(message = "Status must not be blank")
        @Pattern(
                regexp = "ACTIVE|INACTIVE|BLOCKED",
                message = "Status must be one of: ACTIVE, INACTIVE, BLOCKED"
        )
        String status,

        @NotEmpty(message = "User must have at least one role")
        Set<
                @NotNull(message = "Role id must not be null")
                        Integer
                > roleIds
) {}
