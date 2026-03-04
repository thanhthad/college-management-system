package BTEC.ASM.project.modules.academic.mapper;

import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.entity.Offering;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OfferingMapper {

    // Request → Entity (CHỈ DÙNG CHO CREATE / UPDATE)
    @Mapping(source = "subjectId", target = "subject.id")
    @Mapping(source = "termId", target = "term.id")
    @Mapping(source = "classGroupId", target = "classGroup.id")
    Offering toEntity(OfferingRequest request);

    // Entity → Response (DÙNG CHO CLIENT)
    @Mapping(source = "subject.subjectCode", target = "subjectCode")
    @Mapping(source = "subject.subjectName", target = "subjectName")
    @Mapping(source = "term.termCode", target = "termCode")
    @Mapping(source = "term.termName", target = "termName")
    @Mapping(source = "classGroup.groupName", target = "classGroupName")
    OfferingResponse toResponse(Offering entity);

    // Update entity
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "subjectId", target = "subject.id")
    @Mapping(source = "termId", target = "term.id")
    @Mapping(source = "classGroupId", target = "classGroup.id")
    void updateOfferingFromRequest(
            OfferingRequest request,
            @MappingTarget Offering entity
    );
}
