package BTEC.ASM.project.modules.academic.service.impl;

import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;
import BTEC.ASM.project.modules.academic.entity.ClassGroup;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupAlreadyExistsException;
import BTEC.ASM.project.modules.academic.exception.classgroup.ClassGroupNotFoundException;
import BTEC.ASM.project.modules.academic.mapper.ClassGroupMapper;
import BTEC.ASM.project.modules.academic.repository.ClassGroupRepository;
import BTEC.ASM.project.modules.academic.service.ClassGroupService;
import BTEC.ASM.project.modules.academic.service.OfferingService;
import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClassGroupServiceImpl implements ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupMapper classGroupMapper;
    private final OfferingService offeringService;

    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return ((CustomUserDetails) auth.getPrincipal()).getId();
    }

    @Transactional
    @Override
    public ClassGroupResponse create(ClassGroupRequest request,String ip) {
        if(classGroupRepository.existsByGroupName(request.groupName())){
            throw new ClassGroupAlreadyExistsException("Class Group already exists");
        }
        ClassGroup classGroup = classGroupMapper.toEntity(request);
        classGroupRepository.save(classGroup);

        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_CREATED | userId={} | groupName={} | ip={}",
                getUserId(),
                classGroup.getGroupName(),
                ip
        );
        return classGroupMapper.toResponse(classGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> getAll(Pageable pageable,String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findAll(pageable);
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_LIST | userId={} | page={} | size={} | ip={}",
                getUserId(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public ClassGroupResponse getByGroupName(String groupName, String ip) {
        ClassGroup classGroup = classGroupRepository.findByGroupName(groupName).orElseThrow(
                () -> new ClassGroupNotFoundException("ClassGroup not found")
        );
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_NAME | userId={} | groupName={} | ip={}",
                getUserId(),
                classGroup.getGroupName(),
                ip
        );
        return classGroupMapper.toResponse(classGroup);
    }

    @Transactional
    @Override
    public ClassGroupResponse updateByGroupName(String groupName, ClassGroupRequest request, String ip) {
        ClassGroup classGroup = classGroupRepository.findByGroupName(groupName).orElseThrow(
                () -> new ClassGroupNotFoundException("ClassGroup not found")
        );
        if(!groupName.equals(request.groupName()) && classGroupRepository.existsByGroupNameAndIdNot(request.groupName(),classGroup.getId())){
            throw new ClassGroupAlreadyExistsException("ClassGroup already exists");
        }
        classGroupMapper.updateClassGroupFromRequest(request,classGroup);
        classGroupRepository.save(classGroup);

        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_UPDATE | userId={} | groupName={} | ip={}",
                getUserId(),
                request.groupName(),
                ip
        );
        return classGroupMapper.toResponse(classGroup);
    }

    @Transactional
    @Override
    public void deleteByGroupName(String groupName,String ip) {
        ClassGroup classGroup = classGroupRepository.findByGroupName(groupName).orElseThrow(
                () -> new ClassGroupNotFoundException("ClassGroup not found")
        );
        offeringService.validateClassGroupNotInUse(classGroup.getId());
        classGroupRepository.delete(classGroup);
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_DELETE | userId={} | groupName={} | ip={}",
                getUserId(),
                classGroup.getGroupName(),
                ip
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> searchByGroupName(String keyword, Pageable pageable, String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findByGroupNameContainingIgnoreCase(
                keyword,pageable
        );
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_SEARCH_GROUPNAME | userId={} | keyword={} | page={} | size={} | ip={}",
                getUserId(),
                keyword,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> searchByCampus(String campusCode, Pageable pageable, String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findByCampusCode(campusCode,pageable);
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_CAMPUS | userId={} | campusCode={} | page={} | size={} | ip={}",
                getUserId(),
                campusCode,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> searchByDepartment(String departmentCode, Pageable pageable, String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findByDepartmentCode(departmentCode,pageable);
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_DEPARTMENT | userId={} | departmentCode={} | page={} | size={} | ip={}",
                getUserId(),
                departmentCode,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> searchByCampusAndDepartment(String campusCode, String departmentCode, Pageable pageable, String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findByCampusCodeAndDepartmentCode(campusCode,departmentCode,pageable);
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_CAMPUS_AND_DEPARTMENT | userId={} | campusCode={} | departmentCode={} |  page={} | size={} | ip={}",
                getUserId(),
                campusCode,
                departmentCode,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> searchByGroupNameAndCampus(String keyword, String campusCode, Pageable pageable, String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findByGroupNameContainingIgnoreCaseAndCampusCode(
                keyword,campusCode,pageable
        );
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_GROUPNAME_AND_CAMPUS | userId={} | keyword={} | campusCode={} | page={} | size={} | ip={}",
                getUserId(),
                keyword,
                campusCode,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClassGroupResponse> searchByGroupNameAndDepartment(String keyword, String departmentCode, Pageable pageable, String ip) {
        Page<ClassGroup> classGroupPage = classGroupRepository.findByGroupNameContainingIgnoreCaseAndDepartmentCode(
                keyword,departmentCode,pageable
        );
        log.info(
                "CLASSGROUP_EVENT | action=CLASSGROUP_GROUPNAME_AND_DEPARTMENT | userId={} | keyword={} | departmentCode={} | page={} | size={} | ip={}",
                getUserId(),
                keyword,
                departmentCode,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                ip
        );
        return classGroupPage.map(classGroupMapper::toResponse);
    }
}
