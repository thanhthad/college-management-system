package BTEC.ASM.project.academic.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TermRequest(

        @NotBlank(message = "Term code must not be blank")
        @Size(max = 50, message = "Term code must not exceed 50 characters")
        String termCode,

        @Size(max = 100, message = "Term name must not exceed 100 characters")
        String termName,

        @NotNull(message = "Start date is required")
//        @PastOrPresent(message = "Start date must be today or in the past")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        @Future(message = "End date must be in the future")
        LocalDate endDate
) {}
