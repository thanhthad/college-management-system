package BTEC.ASM.project.modules.academic.mapper;

import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.entity.Term;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TermMapper {

    Term toEntity(TermRequest request);

    @Mapping(target = "id",ignore = true)
    void updateTermFromRequest(TermRequest request , @MappingTarget Term entity);

    TermResponse toResponse(Term entity);
}
