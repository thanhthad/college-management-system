package BTEC.ASM.project.academic.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SubjectRequest(

        @NotBlank(message = "Subject code is required")
        String subjectCode,

        @NotBlank(message = "Subject name is required")
        String subjectName,

        String description
) {}
