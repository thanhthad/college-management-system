package BTEC.ASM.project.modules.academic.dto.response;

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

    private Long subjectId;
    private Long termId;
    private Long classGroupId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
