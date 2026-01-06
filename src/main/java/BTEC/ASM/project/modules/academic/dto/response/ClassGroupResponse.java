package BTEC.ASM.project.modules.academic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassGroupResponse {

    private String groupName;
    private String campusCode;
    private String departmentCode;
}
