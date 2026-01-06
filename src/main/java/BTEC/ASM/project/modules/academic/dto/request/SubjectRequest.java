package BTEC.ASM.project.modules.academic.dto.request;

import jakarta.validation.constraints.*;

public record SubjectRequest(

        @NotBlank(message = "Subject code is required")
        @Size(max = 50, message = "Subject code must not exceed 50 characters")
        String subjectCode,

        @NotBlank(message = "Subject name is required")
        @Size(max = 255, message = "Subject name must not exceed 255 characters")
        String subjectName,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description
) {}
