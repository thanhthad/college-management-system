package BTEC.ASM.project.modules.academic.controller;


import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.academic.dto.request.ClassGroupRequest;
import BTEC.ASM.project.modules.academic.dto.response.ClassGroupResponse;
import BTEC.ASM.project.modules.academic.service.ClassGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/classgroups")
@Tag(name = "ClassGroup",description = "Classgroup management APIs")
public class ClassGroupController {

    private final ClassGroupService classGroupService;


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClassGroupRequest request,
                                    HttpServletRequest httpServletRequest){
        ClassGroupResponse subjectResponse = classGroupService.create(request, IpUtils.getClientIp(httpServletRequest));
        return ResponseData.success(subjectResponse,
                "Create successfully",
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAll(
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> subjectResponsePage = classGroupService.getAll(
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(subjectResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/code/{groupName}")
    public ResponseEntity<?> getByGroupName(
            @PathVariable String groupName,
            HttpServletRequest httpServletRequest
    ){
        ClassGroupResponse subjectResponse = classGroupService.getByGroupName(
                groupName,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.success(subjectResponse,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @PutMapping("/{groupName}")
    public ResponseEntity<?> updateByGroupName(
            @PathVariable String groupName,
            @Valid
            @RequestBody ClassGroupRequest request,
            HttpServletRequest httpServletRequest
    ) {
        ClassGroupResponse subjectResponse =
                classGroupService.updateByGroupName(
                        groupName,
                        request,
                        IpUtils.getClientIp(httpServletRequest)
                );

        return ResponseData.success(
                subjectResponse,
                "Update successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("{groupName}")
    public ResponseEntity<?> deleteByGroupName(
            @PathVariable String groupName,
            HttpServletRequest httpServletRequest
    ){
        classGroupService.deleteByGroupName(
                groupName,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.success(
                null,
                "Delete successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/search/group-name")
    public ResponseEntity<?> searchByGroupName(
            @RequestParam String keyword,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> classGroupResponsePage = classGroupService.searchByGroupName(
                keyword,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(classGroupResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/search/campus")
    public ResponseEntity<?> searchByCampus(
            @RequestParam String campusCode,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> classGroupResponsePage = classGroupService.searchByCampus(
                campusCode,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(classGroupResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/search/department")
    public ResponseEntity<?> searchByDepartment(
            @RequestParam String departmentCode,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> classGroupResponsePage = classGroupService.searchByDepartment(
                departmentCode,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(classGroupResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/search/campus-department")
    public ResponseEntity<?> searchByCampusAndDepartment(
            @RequestParam String campusCode,
            @RequestParam String departmentCode,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> classGroupResponsePage = classGroupService.searchByCampusAndDepartment(
                campusCode,
                departmentCode,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(classGroupResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/search/group-campus")
    public ResponseEntity<?> searchByGroupNameandCampus(
            @RequestParam String keyword,
            @RequestParam String campusCode,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> classGroupResponsePage = classGroupService.searchByGroupNameAndCampus(
                keyword,
                campusCode,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(classGroupResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/search/group-department")
    public ResponseEntity<?> searchByGroupNameandDepartment(
            @RequestParam String keyword,
            @RequestParam String departmentCode,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<ClassGroupResponse> classGroupResponsePage = classGroupService.searchByGroupNameAndDepartment(
                keyword,
                departmentCode,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(classGroupResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }
}
