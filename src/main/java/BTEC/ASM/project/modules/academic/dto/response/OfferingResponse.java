package BTEC.ASM.project.modules.academic.dto.response;

import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingResponse {

    private String subjectCode;
    private String subjectName;

    private String termCode;
    private String termName;

    private String classGroupName;

    private LocalDate startDate;
    private LocalDate endDate;

    private OfferingStatus status;
}