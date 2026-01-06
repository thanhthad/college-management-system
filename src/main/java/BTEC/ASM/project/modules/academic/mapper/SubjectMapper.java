package BTEC.ASM.project.modules.academic.mapper;

import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    Subject toEntity(SubjectRequest request);

    SubjectResponse toResponse(Subject entity);

    @Mapping(target = "id",ignore = true)
    void updateSubjectFromRequest(SubjectRequest request , @MappingTarget Subject entity);
}
