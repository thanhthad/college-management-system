package BTEC.ASM.project.modules.academic.dto.request;

import jakarta.validation.constraints.*;

public record ClassGroupRequest(

        @NotBlank(message = "Group name is required")
        @Size(max = 100, message = "Group name must not exceed 100 characters")
        String groupName,

        @Size(max = 50, message = "Campus code must not exceed 50 characters")
        String campusCode,

        @Size(max = 50, message = "Department code must not exceed 50 characters")
        String departmentCode
) {}
