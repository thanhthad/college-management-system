package BTEC.ASM.project.modules.academic.mapper;
import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassGroupMapper {

    ClassGroup toEntity(ClassGroupRequest request);

    ClassGroupResponse toResponse(ClassGroup entity);

    @Mapping(target = "id",ignore = true)
    void updateClassGroupFromRequest(ClassGroupRequest request , @MappingTarget ClassGroup entity);
}
