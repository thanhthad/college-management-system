package BTEC.ASM.project.modules.academic.dto.request;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminOfferingFilter {
    private Long subjectId;
    private Long termId;
    private Long classGroupId;
    private OfferingStatus status;
    private LocalDate startDateFrom;
    private LocalDate endDateTo;
}