package BTEC.ASM.project.modules.academic.dto.request;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AdminOfferingFilter (

        @Positive(message = "Subject id must be a positive number")
        Long subjectId,

        @Positive(message = "Term id must be a positive number")
        Long termId,

        @Positive(message = "Class group id must be a positive number")
        Long classGroupId,

        LocalDate startDate,

        LocalDate endDate,

        OfferingStatus status
) {
    @AssertTrue(message = "startDate must be before or equal to endDate")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !startDate.isAfter(endDate);
    }
}