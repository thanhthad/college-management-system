package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.AdminOfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OfferingService {

    void validateTermNotInUse(Long termId);

    void validateSubjectNotInUse(Long subjectId);

    void validateClassGroupNotInUse(Long classGroupId);

    Page<OfferingResponse> filterOffering(
            OfferingFilter filter,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> filterOfferingAdmin(
            AdminOfferingFilter filter,
            Pageable pageable,
            String ip
    );

    OfferingResponse create(OfferingRequest req, String ip);

    void delete(Long offeringId, String ip);

    OfferingResponse update(
            Long offeringId,
            OfferingRequest req,
            String ip
    );
}
