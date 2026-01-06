package BTEC.ASM.project.modules.academic.mapper;

import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Offering;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OfferingMapper {

    // Map request → entity
    @Mapping(source = "subjectId", target = "subject.id")
    @Mapping(source = "termId", target = "term.id")
    @Mapping(source = "classGroupId", target = "classGroup.id")
    Offering toEntity(OfferingRequest request);

    // Map entity → response
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "term.id", target = "termId")
    @Mapping(source = "classGroup.id", target = "classGroupId")
    OfferingResponse toResponse(Offering entity);

    @Mapping(target = "id",ignore = true)
    @Mapping(source = "subjectId", target = "subject.id")
    @Mapping(source = "termId", target = "term.id")
    @Mapping(source = "classGroupId", target = "classGroup.id")
    void updateOfferingFromRequest(OfferingRequest request , @MappingTarget Offering entity);
}
