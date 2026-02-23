package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.exception.term.TermAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.term.TermNotFoundException;
import BTEC.ASM.project.modules.academic.mapper.TermMapper;
import BTEC.ASM.project.modules.academic.repository.TermRepository;
import BTEC.ASM.project.modules.academic.service.OfferingService;
import BTEC.ASM.project.modules.academic.service.TermService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;
    private final TermMapper termMapper;
    private final OfferingService offeringService;

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((CustomUserDetails) auth.getPrincipal()).getId();
    }

    // ===== CREATE =====
    @Override
    public TermResponse create(TermRequest request, String ip) {
        try {
            Term saved = termRepository.save(termMapper.toEntity(request));

            log.info(
                    "TERM_EVENT | action=TERM_CREATED | userId={} | termCode={} | ip={}",
                    getUserId(),
                    saved.getTermCode(),
                    ip
            );

            return termMapper.toResponse(saved);

        } catch (DataIntegrityViolationException ex) {
            throw new TermAlreadyExistsException("Term already exists");
        }
    }

    // ===== FIND BY CODE =====
    @Override
    public TermResponse findByTermCode(String termCode, String ip) {
        Term term = termRepository.findByTermCode(termCode)
                .orElseThrow(() -> new TermNotFoundException("Term not found"));

        log.info(
                "TERM_EVENT | action=TERM_FETCHED | userId={} | termCode={} | ip={}",
                getUserId(),
                termCode,
                ip
        );

        return termMapper.toResponse(term);
    }

    // ===== VALIDATE EXISTS =====
    @Override
    public void validateTermExists(String termCode, String ip) {
        if (!termRepository.existsByTermCode(termCode)) {
            throw new TermNotFoundException("Term not found");
        }
//
//        log.info(
//                "AUTH_EVENT | action=TERM_VALIDATED | userId={} | termCode={} | ip={}",
//                getUserId(),
//                termCode,
//                ip
//        );
    }

    // ===== FILTER BY START DATE =====
    @Override
    public Page<TermResponse> findByStartDateAfter(
            LocalDate date,
            Pageable pageable,
            String ip
    ) {
        Page<Term> records =
                termRepository.findByStartDateAfter(date, pageable);

        log.info(
                "TERM_EVENT | action=TERM_LIST_START_AFTER | userId={} | date={} | ip={}",
                getUserId(),
                date,
                ip
        );

        return records.map(termMapper::toResponse);
    }

    // ===== CURRENT TERMS =====
    @Override
    public Page<TermResponse> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate now1,
            LocalDate now2,
            Pageable pageable,
            String ip
    ) {
        Page<Term> records =
                termRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        now1, now2, pageable
                );

        log.info(
                "TERM_EVENT | action=TERM_LIST_ACTIVE | userId={} | ip={}",
                getUserId(),
                ip
        );

        return records.map(termMapper::toResponse);
    }

    // ===== FIND ALL =====
    @Override
    public Page<TermResponse> findAll(Pageable pageable, String ip) {
        Page<Term> records = termRepository.findAll(pageable);

        log.info(
                "TERM_EVENT | action=TERM_LIST_ALL | userId={} | page={} | size={} | ip={}",
                getUserId(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );

        return records.map(termMapper::toResponse);
    }

    // ===== UPDATE =====
    @Override
    public TermResponse updateByTermCode(
            String termCode,
            TermRequest request,
            String ip
    ) {
        Term term = termRepository.findByTermCode(termCode)
                .orElseThrow(() -> new TermNotFoundException("Term not found"));

        if (request.termCode() != null &&
                termRepository.existsByTermCodeAndIdNot(
                        request.termCode(), term.getId())) {
            throw new TermAlreadyExistsException("Term already exists");
        }

        termMapper.updateTermFromRequest(request, term);
        termRepository.save(term);

        log.info(
                "TERM_EVENT | action=TERM_UPDATED | userId={} | termCode={} | ip={}",
                getUserId(),
                term.getTermCode(),
                ip
        );

        return termMapper.toResponse(term);
    }

    // ===== DELETE =====
    @Override
    public void deleteByTermCode(String termCode, String ip) {
        Term term = termRepository.findByTermCode(termCode)
                .orElseThrow(() -> new TermNotFoundException("Term not found"));

        offeringService.validateTermNotInUse(term.getId());
        termRepository.delete(term);

        log.info(
                "TERM_EVENT | action=TERM_DELETED | userId={} | termCode={} | ip={}",
                getUserId(),
                termCode,
                ip
        );
    }
}
