package BTEC.ASM.project.modules.enrollment.dto.request;

import BTEC.ASM.project.modules.enrollment.enums.StaffRole;
import jakarta.validation.constraints.NotNull;

public record OfferingStaffRequest(

        @NotNull(message = "Offering id must not be null")
        Long offeringId,

        @NotNull(message = "User id must not be null")
        Long userId,

        @NotNull(message = "Staff role must not be null")
        StaffRole staffRole
) {}