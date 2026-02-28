package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupNotFoundException;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.ClassGroupDomainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClassGroupDomainServiceImpl implements ClassGroupDomainService {
    private final ClassGroupRepository classGroupRepository;


    @Override
    public ClassGroup getByClassGroupName(String groupName) {
        ClassGroup classGroup = classGroupRepository.findByGroupName(groupName).orElseThrow(
                () -> new ClassGroupNotFoundException("ClassGroup Not Found")
        );
        return classGroup;
    }

    @Override
    public boolean existsByClassGroupName(String groupName) {
        return classGroupRepository.existsByGroupName(groupName);
    }
}
