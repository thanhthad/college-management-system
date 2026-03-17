package BTEC.ASM.project.modules.enrollment.mapper;

import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentRequest;
import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentUpdateRequest;
import BTEC.ASM.project.modules.enrollment.dto.response.EnrollmentResponse;
import BTEC.ASM.project.modules.enrollment.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "offeringId", target = "offering.id")
    @Mapping(source = "studentUserId", target = "student.id")
    Enrollment toEntity(EnrollmentRequest request);


    @Mapping(source = "student.userCode", target = "studentCode")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "offering.subject.subjectCode", target = "subjectCode")
    @Mapping(source = "offering.subject.subjectName", target = "subjectName")
    @Mapping(source = "offering.term.termCode", target = "termCode")
    @Mapping(source = "offering.term.termName", target = "termCode")
    @Mapping(source = "offering.classGroup.groupName", target = "classGroupName")
    @Mapping(source = "offering.classGroup.campusCode", target = "classGroupCode")
    EnrollmentResponse toResponse(Enrollment enrollment);


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "offeringId", target = "offering.id")
    @Mapping(source = "studentUserId", target = "student.id")
    void updateEnrollmentFromRequest(
            EnrollmentUpdateRequest request,
            @MappingTarget Enrollment entity
    );
}
