package BTEC.ASM.project.academic.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OfferingRequest(

        @NotNull(message = "Subject id is required")
        Long subjectId,

        @NotNull(message = "Term id is required")
        Long termId,

        @NotNull(message = "Class group id is required")
        Long classGroupId,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        String status
) {}
