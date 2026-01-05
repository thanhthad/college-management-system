package BTEC.ASM.project.identity.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "User code không được để trống")
    @Size(max = 50)
    private String userCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 255)
    private String fullName;

    @Email(message = "Email không đúng định dạng")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "Status không được để trống")
    @Pattern(
            regexp = "ACTIVE|INACTIVE|BLOCKED",
            message = "Status phải là ACTIVE, INACTIVE hoặc BLOCKED"
    )
    private String status;

    @NotEmpty(message = "User phải có ít nhất 1 role")
    private Set<
            @NotNull(message = "Role id không được null")
                    Integer
            > roleIds;
}
