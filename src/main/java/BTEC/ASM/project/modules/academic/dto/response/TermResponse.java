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
public class TermResponse {

    private String termCode;
    private String termName;
    private LocalDate startDate;
    private LocalDate endDate;
}
