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
    TermResponse create(TermRequest request,String ip);

    TermResponse findByTermCode(String termCode,String ip);

    void validateTermExists(String termCode,String ip);

    Page<TermResponse> findByStartDateAfter(LocalDate date, Pageable pageable,String ip);

    Page<TermResponse> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate now1,
            LocalDate now2,
            Pageable pageable,String ip
    );

    Page<TermResponse> findAll(Pageable pageable,String ip);

    TermResponse updateByTermCode(String termCode, TermRequest request,String ip);

    void deleteByTermCode(String termCode,String ip);
}
