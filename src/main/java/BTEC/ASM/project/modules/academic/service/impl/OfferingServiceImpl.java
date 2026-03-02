package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.OfferingSpecification;
import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.mapper.OfferingMapper;
import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final TermDomainService termDomainService;
    private final SubjectDomainService subjectDomainService;
    private final OfferingMapper offeringMapper;
    private final ClassGroupDomainService classGroupDomainService;

    @Override
    @Transactional(readOnly = true)
    public void validateTermNotInUse(Long termId) {
        if (offeringRepository.existsByTermId(termId)) {
            throw new TermAlreadyExistsException(
                    "Term is already used in offerings"
            );
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void validateSubjectNotInUse(Long subjectId) {
        if (offeringRepository.existsBySubjectId(subjectId)) {
            throw new SubjectAlreadyExistsException(
                    "Subject is already used in offerings"
            );
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void validateClassGroupNotInUse(Long classGroupId) {
        if (offeringRepository.existsByClassGroupId(classGroupId)) {
            throw new ClassGroupAlreadyExistsException(
                    "Class group is already used in offerings"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OfferingResponse> search(
            OfferingFilter filter,
            Pageable pageable,
            String ip
    ) {
        Page<Offering> page = offeringRepository.findAll(
                OfferingSpecification.build(filter),
                pageable
        );

        return page.map(offeringMapper::toResponse);
    }

}
