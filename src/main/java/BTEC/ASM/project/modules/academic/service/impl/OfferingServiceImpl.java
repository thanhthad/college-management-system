package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import BTEC.ASM.project.modules.academic.exception.InvalidDateRangeException;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingNotFoundException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.mapper.OfferingMapper;
import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    @Override
    public OfferingResponse getBySubjectCodeAndTermCodeAndGroupName(String subjectCode, String termCode, String groupName, String ip) {
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        Term term = termDomainService.getByTermCode(termCode);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);

        Offering offering = offeringRepository.findBySubjectAndTermAndClassGroup(
                subject,
                term,
                classGroup
        ).orElseThrow(
                () -> new OfferingNotFoundException("Offering Not Found")
        );
        return offeringMapper.toResponse(offering);
    }

    @Override
    public OfferingResponse getBySubjectIdAndTermIdAndGroupId(Long subjectId, Long termId, Long groupId, String ip) {
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        Term term = termDomainService.getByTermId(termId);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupId);

        Offering offering = offeringRepository.findBySubjectAndTermAndClassGroup(
                subject,
                term,
                classGroup
        ).orElseThrow(
                () -> new OfferingNotFoundException("Offering Not Found")
        );
        return offeringMapper.toResponse(offering);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectCode(String subjectCode, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        Page<Offering> offeringPage = offeringRepository.findAllBySubject(subject,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectId(Long subjectId, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        Page<Offering> offeringPage = offeringRepository.findAllBySubject(subject,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermCode(String termCode, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermCode(termCode);
        Page<Offering> offeringPage = offeringRepository.findAllByTerm(term,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermId(Long termId, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermId(termId);
        Page<Offering> offeringPage = offeringRepository.findAllByTerm(term,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByGroupName(String groupName, Pageable pageable, String ip) {
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllByClassGroup(classGroup,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByGroupId(Long groupId, Pageable pageable, String ip) {
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupId);
        Page<Offering> offeringPage = offeringRepository.findAllByClassGroup(classGroup,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByStatus(OfferingStatus status, Pageable pageable, String ip) {
        Page<Offering> offeringPage = offeringRepository.findAllByStatus(status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjecCodeAndTermCode(String subjectCode, String termCode, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        Term term = termDomainService.getByTermCode(termCode);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndTerm(subject,term,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjecIdAndTermId(Long subjectId, Long termId, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        Term term = termDomainService.getByTermId(termId);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndTerm(subject,term,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjecCodeAndClassGroupName(String subjectCode, String groupName, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndClassGroup(subject,classGroup,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjecIdAndClassGroupId(Long subjectId, Long groupId, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupId);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndClassGroup(subject,classGroup,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermCodeAndClassGroupName(String termCode, String groupName, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermCode(termCode);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndClassGroup(term,classGroup,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermIdAndClassGroupId(Long termId, Long groupName, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermId(termId);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndClassGroup(term,classGroup,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermCodeAndStatus(String termCode, OfferingStatus status, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermCode(termCode);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndStatus(term,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermIdAndStatus(Long termId, OfferingStatus status, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermId(termId);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndStatus(term,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByClassGroupNameAndStatus(String groupName, OfferingStatus status, Pageable pageable, String ip) {
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllByClassGroupAndStatus(classGroup,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByClassGroupIdAndStatus(Long groupId, OfferingStatus status, Pageable pageable, String ip) {
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupId);
        Page<Offering> offeringPage = offeringRepository.findAllByClassGroupAndStatus(classGroup,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectCodeAndStatus(String subjectCode, OfferingStatus status, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndStatus(subject,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectIdAndStatus(Long subjectId, OfferingStatus status, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndStatus(subject,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermIdAndClassGroupIdAndStatus(Long termId, Long groupId, OfferingStatus status, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermId(termId);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupId);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndClassGroupAndStatus(term,classGroup,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermCodeAndClassGroupNameAndStatus(String termCode, String groupName, OfferingStatus status, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermCode(termCode);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndClassGroupAndStatus(term,classGroup,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectIdAndTermIdAndStatus(Long subjectId, Long termId, OfferingStatus status, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermId(termId);
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndTermAndStatus(subject,term,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectCodeAndTermCodeAndStatus(String subjectCode, String termCode, OfferingStatus status, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermCode(termCode);
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndTermAndStatus(subject,term,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectIdAndClassGroupIdAndStatus(Long subjectId, Long groupId, OfferingStatus status, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectId(subjectId);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupId(groupId);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndClassGroupAndStatus(subject,classGroup,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllBySubjectCodeAndClassGroupNameAndStatus(String subjectCode, String groupName, OfferingStatus status, Pageable pageable, String ip) {
        Subject subject = subjectDomainService.getBySubjectCode(subjectCode);
        ClassGroup classGroup = classGroupDomainService.getByClassGroupName(groupName);
        Page<Offering> offeringPage = offeringRepository.findAllBySubjectAndClassGroupAndStatus(subject,classGroup,status,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }


    @Override
    public Page<OfferingResponse> getAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate todayStart, LocalDate todayEnd, Pageable pageable, String ip) {
        if (todayStart.isAfter(todayEnd)) {
            throw new InvalidDateRangeException(
                    "startDate must be before or equal to endDate"
            );
        }
        Page<Offering> offeringPage =
                offeringRepository
                        .findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                todayStart,
                                todayEnd,
                                pageable
                        );

        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermCodeAndEndDateAfter(String termCode, LocalDate date, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermCode(termCode);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndEndDateAfter(term,date,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

    @Override
    public Page<OfferingResponse> getAllByTermIdAndEndDateAfter(Long termId, LocalDate date, Pageable pageable, String ip) {
        Term term = termDomainService.getByTermId(termId);
        Page<Offering> offeringPage = offeringRepository.findAllByTermAndEndDateAfter(term,date,pageable);
        return offeringPage.map(offeringMapper::toResponse);
    }

}
