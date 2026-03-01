package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Term;
import BTEC.ASM.project.modules.academic.enums.OfferingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OfferingService {

    void validateTermNotInUse(Long termId);

    void validateSubjectNotInUse(Long subjectId);

    void validateClassGroupNotInUse(Long classGroupId);

    OfferingResponse getBySubjectCodeAndTermCodeAndGroupName(
            String subjectCode,
            String termCode,
            String groupName,
            String ip
    );

    OfferingResponse getBySubjectIdAndTermIdAndGroupId(
            Long subjectId,
            Long termId,
            Long groupId,
            String ip
    );

    //GET ALL By Subject (ID,CODE)
    Page<OfferingResponse> getAllBySubjectCode(
            String subjectCode,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectId(
            Long subjectId,
            Pageable pageable,
            String ip
    );

    //Get ALL By Term (ID,CODE)
    Page<OfferingResponse> getAllByTermCode(
            String termCode,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermId(
            Long termId,
            Pageable pageable,
            String ip
    );

    //Get All By Group (ID, CODE)
    Page<OfferingResponse> getAllByGroupName(
            String groupName,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByGroupId(
            Long groupId,
            Pageable pageable,
            String ip
    );

    //Get All By Status
    Page<OfferingResponse> getAllByStatus(
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjecCodeAndTermCode(
            String subjectCode,
            String termCode,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjecIdAndTermId(
            Long subjectId,
            Long termId,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjecCodeAndClassGroupName(
            String subjectCode,
            String groupName,
            Pageable pageable,
            String ip
    );
    Page<OfferingResponse> getAllBySubjecIdAndClassGroupId(
            Long subjectId,
            Long groupId,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermCodeAndClassGroupName(
            String termCode,
            String groupName,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermIdAndClassGroupId(
            Long termId,
            Long groupName,
            Pageable pageable,
            String ip
    );

//    STATUS: OPEN, ONGOING, CLOSED
    Page<OfferingResponse> getAllByTermCodeAndStatus(
            String termCode,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermIdAndStatus(
            Long termId,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByClassGroupNameAndStatus(
            String groupName,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByClassGroupIdAndStatus(
            Long groupId,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectCodeAndStatus(
            String subjectCode,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectIdAndStatus(
            Long subjectId,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermIdAndClassGroupIdAndStatus(
            Long termId,
            Long groupId,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermCodeAndClassGroupNameAndStatus(
            String termCode,
            String groupName,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectIdAndTermIdAndStatus(
            Long subjectId,
            Long termId,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectCodeAndTermCodeAndStatus(
            String subjectCode,
            String termCode,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectIdAndClassGroupIdAndStatus(
            Long subjectId,
            Long groupId,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllBySubjectCodeAndClassGroupNameAndStatus(
            String subjectCode,
            String groupName,
            OfferingStatus status,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate todayStart,
            LocalDate todayEnd,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermCodeAndEndDateAfter(
            String termCode,
            LocalDate date,
            Pageable pageable,
            String ip
    );

    Page<OfferingResponse> getAllByTermIdAndEndDateAfter(
            Long termId,
            LocalDate date,
            Pageable pageable,
            String ip
    );
}
