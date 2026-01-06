package BTEC.ASM.project.modules.identity.dto.request;

import jakarta.validation.constraints.*;
import java.util.Set;

public record UpdateUserRequest(

        @Size(max = 255, message = "Full name must not exceed 255 characters")
        String fullName,

        @Email(message = "Email must be a valid email address")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @Pattern(
                regexp = "ACTIVE|INACTIVE|BLOCKED",
                message = "Status must be one of: ACTIVE, INACTIVE, BLOCKED"
        )
        String status,

        Set<
                @NotNull(message = "Role id must not be null")
                        Integer
                > roleIds
) {}
