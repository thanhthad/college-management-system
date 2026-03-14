package BTEC.ASM.project.modules.enrollment.dto.request;

import BTEC.ASM.project.modules.enrollment.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;

public record EnrollmentUpdateRequest(

        @NotNull(message = "Enrollment status must not be null")
        EnrollmentStatus enrollStatus
) {}