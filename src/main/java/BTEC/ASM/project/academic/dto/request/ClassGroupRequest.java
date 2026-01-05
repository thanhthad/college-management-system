package BTEC.ASM.project.academic.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClassGroupRequest(

        @NotBlank(message = "Group name is required")
        String groupName,

        String campusCode,
        String departmentCode
) {}
