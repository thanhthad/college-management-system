package BTEC.ASM.project.modules.enrollment.dto.request;

import BTEC.ASM.project.modules.enrollment.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(

        @NotNull(message = "Offering id must not be null")
        Long offeringId,

        @NotNull(message = "Student user id must not be null")
        Long studentUserId,

        @NotNull(message = "Enrollment status must not be null")
        EnrollmentStatus enrollStatus
) {}