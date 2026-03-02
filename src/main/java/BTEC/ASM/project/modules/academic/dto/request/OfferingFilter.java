package BTEC.ASM.project.modules.academic.dto.request;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OfferingFilter {

    private String subjectCode;
    private String termCode;
    private String classGroupName;
    private OfferingStatus status;

    private LocalDate startDateFrom;
    private LocalDate endDateTo;
}
