package BTEC.ASM.project.modules.academic.dto.request;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record OfferingRequest (

    @NotNull(message = "Subject id is required")
    @Positive(message = "Subject id must be a positive number")
    Long subjectId,

    @NotNull(message = "Term id is required")
    @Positive(message = "Term id must be a positive number")
    Long termId,

    @NotNull(message = "Class group id is required")
    @Positive(message = "Class group id must be a positive number")
    Long classGroupId,

    @NotNull(message = "Start date is required")
    LocalDate startDate,

    @NotNull(message = "End date is required")
    LocalDate endDate,

    @NotNull(message = "Status is required")
    OfferingStatus status
) {}
