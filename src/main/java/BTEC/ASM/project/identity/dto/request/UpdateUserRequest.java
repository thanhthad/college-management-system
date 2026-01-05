package BTEC.ASM.project.identity.dto.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {

    @Size(max = 255)
    private String fullName;

    @Email(message = "Email không đúng định dạng")
    @Size(max = 255)
    private String email;

    @Pattern(
            regexp = "ACTIVE|INACTIVE|BLOCKED",
            message = "Status phải là ACTIVE, INACTIVE hoặc BLOCKED"
    )
    private String status;

    private Set<
            @NotNull(message = "Role id không được null")
                    Integer
            > roleIds;
}