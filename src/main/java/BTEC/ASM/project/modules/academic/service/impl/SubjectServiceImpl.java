package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.entity.Subject;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.subject.SubjectNotFoundException;
import BTEC.ASM.project.modules.academic.mapper.SubjectMapper;
import BTEC.ASM.project.modules.academic.repository.SubjectRepository;
import BTEC.ASM.project.modules.academic.service.OfferingService;
import BTEC.ASM.project.modules.academic.service.SubjectService;
import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final OfferingService offeringService;

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((CustomUserDetails) auth.getPrincipal()).getId();
    }

    @Transactional
    @Override
    public SubjectResponse create(SubjectRequest request, String ip) {
        if (subjectRepository.existsBySubjectCode(request.subjectCode())) {
            throw new SubjectAlreadyExistsException("Subject already exists");
        }

        Subject subject = subjectMapper.toEntity(request);
        Subject saved = subjectRepository.save(subject);

        log.info(
                "SUBJECT_EVENT | action=SUBJECT_CREATED | userId={} | subjectCode={} | ip={}",
                getUserId(),
                saved.getSubjectCode(),
                ip
        );

        return subjectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SubjectResponse> getAll(Pageable pageable, String ip) {
        Page<Subject> results = subjectRepository.findAll(pageable);

        log.info(
                "SUBJECT_EVENT | action=SUBJECT_LIST | userId={} | page={} | size={} | ip={}",
                getUserId(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );

        return results.map(subjectMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public SubjectResponse getBySubjectCode(String subjectCode, String ip) {
        Subject subject = subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));

        log.info(
                "SUBJECT_EVENT | action=SUBJECT_VIEW | userId={} | subjectCode={} | ip={}",
                getUserId(),
                subjectCode,
                ip
        );

        return subjectMapper.toResponse(subject);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SubjectResponse> getBySubjectName(
            String subjectName,
            Pageable pageable,
            String ip
    ) {
        Page<Subject> results =
                subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName, pageable);

        log.info(
                "SUBJECT_EVENT | action=SUBJECT_SEARCH | userId={} | keyword={} | page={} | size={} | ip={}",
                getUserId(),
                subjectName,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );

        return results.map(subjectMapper::toResponse);
    }

    @Transactional
    @Override
    public SubjectResponse updateBySubjectCode(
            String subjectCode,
            SubjectRequest request,
            String ip
    ) {
        Subject subject = subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));

        if (!request.subjectCode().equals(subject.getSubjectCode())
                && subjectRepository.existsBySubjectCodeAndIdNot(
                request.subjectCode(),
                subject.getId()
        )) {
            throw new SubjectAlreadyExistsException("SubjectCode already exists");
        }

        subjectMapper.updateSubjectFromRequest(request, subject);
        Subject saved = subjectRepository.save(subject);

        log.info(
                "SUBJECT_EVENT | action=SUBJECT_UPDATE | userId={} | subjectCode={} | ip={}",
                getUserId(),
                request.subjectCode(),
                ip
        );

        return subjectMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public void deleteBySubjectCode(String subjectCode,String ip) {
        Subject subject = subjectRepository.findBySubjectCode(subjectCode)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));
        offeringService.validateSubjectNotInUse(subject.getId());
        subjectRepository.delete(subject);
        log.info(
                "SUBJECT_EVENT | action=SUBJECT_DELETED | userId={} | subjectCode={} | ip={}",
                getUserId(),
                subjectCode,
                ip
        );
    }
}
