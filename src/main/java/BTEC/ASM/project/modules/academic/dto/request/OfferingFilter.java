package BTEC.ASM.project.modules.academic.dto.request;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record OfferingFilter (

        @Size(max = 50, message = "Subject code must not exceed 50 characters")
        String subjectCode,

        @Size(max = 50, message = "Term code must not exceed 50 characters")
        String termCode,

        @Size(max = 100, message = "Group name must not exceed 100 characters")
        String classGroupName,

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
