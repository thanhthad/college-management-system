package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.entity.Term;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TermService {

    TermResponse findByTermCode(String termCode);

    void existsByTermCode(String termCode);

    Page<TermResponse> findByStartDateAfter(LocalDate date, Pageable pageable);

    Page<TermResponse> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate now1,
            LocalDate now2,
            Pageable pageable
    );

    Page<TermResponse> findAll(Pageable pageable);

    TermResponse updateByTermCode(String termCode, TermRequest request);

    void deleteByTermCode(String termCode);
}
