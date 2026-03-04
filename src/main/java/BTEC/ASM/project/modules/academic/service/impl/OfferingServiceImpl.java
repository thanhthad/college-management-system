package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.OfferingSpecification;
import BTEC.ASM.project.modules.academic.dto.request.AdminOfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.entity.Offering;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingConflictException;
import BTEC.ASM.project.modules.academic.exception.offering.OfferingNotFoundException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.mapper.OfferingMapper;
import BTEC.ASM.project.modules.academic.repository.OfferingRepository;
import BTEC.ASM.project.modules.academic.service.*;
import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final TermDomainService termDomainService;
    private final SubjectDomainService subjectDomainService;
    private final OfferingMapper offeringMapper;
    private final ClassGroupDomainService classGroupDomainService;

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((CustomUserDetails) auth.getPrincipal()).getId();
    }

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

    // ================= FILTER (CLIENT) =================
    @Override
    @Transactional(readOnly = true)
    public Page<OfferingResponse> filterOffering(
            OfferingFilter filter,
            Pageable pageable,
            String ip
    ) {

        Page<Offering> page = offeringRepository.findAll(
                OfferingSpecification.byClientFilter(filter),
                pageable
        );

        log.info(
                "OFFERING_EVENT | action=OFFERING_FILTER_CLIENT | userId={} | page={} | size={} | ip={}",
                getUserId(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );

        return page.map(offeringMapper::toResponse);
    }

    // ================= FILTER (ADMIN) =================
    @Override
    @Transactional(readOnly = true)
    public Page<OfferingResponse> filterOfferingAdmin(
            AdminOfferingFilter filter,
            Pageable pageable,
            String ip
    ) {

        Page<Offering> page = offeringRepository.findAll(
                OfferingSpecification.byAdminFilter(filter),
                pageable
        );

        log.info(
                "OFFERING_EVENT | action=OFFERING_FILTER_ADMIN | userId={} | page={} | size={} | ip={}",
                getUserId(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );

        return page.map(offeringMapper::toResponse);
    }

    // ================= CREATE =================
    @Override
    @Transactional
    public OfferingResponse create(OfferingRequest req, String ip) {

        Subject subject = subjectDomainService.getBySubjectId(req.subjectId());
        Term term = termDomainService.getByTermId(req.termId());
        ClassGroup group = classGroupDomainService.getByClassGroupId(req.classGroupId());

        Offering offering = Offering.builder()
                .subject(subject)
                .term(term)
                .classGroup(group)
                .startDate(req.startDate())
                .endDate(req.endDate())
                .status(req.status())
                .build();

        try {
            offeringRepository.save(offering);
        } catch (DataIntegrityViolationException ex) {

            log.warn(
                    "OFFERING_EVENT | action=OFFERING_CREATE | userId={} | status=FAIL | reason=CONFLICT | subjectId={} | termId={} | classGroupId={} | ip={}",
                    getUserId(),
                    req.subjectId(),
                    req.termId(),
                    req.classGroupId(),
                    ip
            );

            throw new OfferingAlreadyExistsException("Offering already exists");
        }

        log.info(
                "OFFERING_EVENT | action=OFFERING_CREATE | userId={} | status=SUCCESS | subjectId={} | termId={} | classGroupId={} | ip={}",
                getUserId(),
                req.subjectId(),
                req.termId(),
                req.classGroupId(),
                ip
        );

        return offeringMapper.toResponse(offering);
    }

    // ================= UPDATE =================
    @Override
    @Transactional
    public OfferingResponse update(
            Long offeringId,
            OfferingRequest req,
            String ip
    ) {

        Offering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> {
                    log.warn(
                            "OFFERING_EVENT | action=OFFERING_UPDATE | userId={} | status=FAIL | reason=NOT_FOUND | offeringId={} | ip={}",
                            getUserId(),
                            offeringId,
                            ip
                    );
                    return new OfferingNotFoundException("Offering not found");
                });

        offering.setStartDate(req.startDate());
        offering.setEndDate(req.endDate());
        offering.setStatus(req.status());

        try {
            offeringRepository.save(offering);
        } catch (DataIntegrityViolationException ex) {

            log.warn(
                    "OFFERING_EVENT | action=OFFERING_UPDATE | userId={} | status=FAIL | reason=CONFLICT | offeringId={} | ip={}",
                    getUserId(),
                    offeringId,
                    ip
            );

            throw new OfferingConflictException("Offering conflicts with existing data");
        }

        log.info(
                "OFFERING_EVENT | action=OFFERING_UPDATE | userId={} | status=SUCCESS | offeringId={} | ip={}",
                getUserId(),
                offeringId,
                ip
        );

        return offeringMapper.toResponse(offering);
    }

    // ================= DELETE =================
    @Override
    @Transactional
    public void delete(Long offeringId, String ip) {

        Offering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> {
                    log.warn(
                            "OFFERING_EVENT | action=OFFERING_DELETE | userId={} | status=FAIL | reason=NOT_FOUND | offeringId={} | ip={}",
                            getUserId(),
                            offeringId,
                            ip
                    );
                    return new OfferingNotFoundException("Offering not found");
                });

        offeringRepository.delete(offering);

        log.info(
                "OFFERING_EVENT | action=OFFERING_DELETE | userId={} | status=SUCCESS | offeringId={} | ip={}",
                getUserId(),
                offeringId,
                ip
        );
    }
}
