package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.mapper.OfferingMapper;
import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.OfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;

    @Override
    public void validateTermNotInUse(Long termId) {
        if (offeringRepository.existsByTermId(termId)) {
            throw new TermAlreadyExistsException(
                    "Term is already used in offerings"
            );
        }
    }

    @Override
    public void validateSubjectNotInUse(Long subjectId) {
        if (offeringRepository.existsBySubjectId(subjectId)) {
            throw new SubjectAlreadyExistsException(
                    "Subject is already used in offerings"
            );
        }
    }

    @Override
    public void validateClassGroupNotInUse(Long classGroupId) {
        if (offeringRepository.existsByClassGroupId(classGroupId)) {
            throw new ClassGroupAlreadyExistsException(
                    "Class group is already used in offerings"
            );
        }
    }
}
