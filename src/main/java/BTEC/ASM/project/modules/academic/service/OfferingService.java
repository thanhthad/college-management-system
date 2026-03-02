package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OfferingService {

    void validateTermNotInUse(Long termId);

    void validateSubjectNotInUse(Long subjectId);

    void validateClassGroupNotInUse(Long classGroupId);

    Page<OfferingResponse> search(
            OfferingFilter filter,
            Pageable pageable,
            String ip
    );
}
