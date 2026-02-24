package BTEC.ASM.project.modules.academic.service;

import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassGroupService {

    /* =========================
     * CRUD
     * ========================= */

    ClassGroupResponse create(ClassGroupRequest request,String ip);

    Page<ClassGroupResponse> getAll(Pageable pageable,String ip);

    ClassGroupResponse getByGroupName(String groupName, String ip);

    ClassGroupResponse updateByGroupName(
            String groupName,
            ClassGroupRequest request,
            String ip
    );

    void deleteByGroupName(String groupName);

    /* =========================
     * SEARCH / FILTER
     * ========================= */

    Page<ClassGroupResponse> searchByGroupName(
            String keyword,
            Pageable pageable,
            String ip
    );

    Page<ClassGroupResponse> searchByCampus(
            String campusCode,
            Pageable pageable,
            String ip
    );

    Page<ClassGroupResponse> searchByDepartment(
            String departmentCode,
            Pageable pageable,
            String ip
    );

    Page<ClassGroupResponse> searchByCampusAndDepartment(
            String campusCode,
            String departmentCode,
            Pageable pageable,
            String ip
    );

    Page<ClassGroupResponse> searchByGroupNameAndCampus(
            String keyword,
            String campusCode,
            Pageable pageable,
            String ip
    );

    Page<ClassGroupResponse> searchByGroupNameAndDepartment(
            String keyword,
            String departmentCode,
            Pageable pageable,
            String ip
    );
}