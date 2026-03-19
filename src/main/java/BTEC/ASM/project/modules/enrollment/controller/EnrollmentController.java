package BTEC.ASM.project.modules.enrollment.controller;


import BTEC.ASM.project.common.response.ApiResponse;
import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentRequest;
import BTEC.ASM.project.modules.enrollment.dto.request.EnrollmentUpdateRequest;
import BTEC.ASM.project.modules.enrollment.dto.response.EnrollmentResponse;
import BTEC.ASM.project.modules.enrollment.service.EnrollmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments",description = "Enrollment management APIs")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponse>> create(
            @Valid
            @RequestBody EnrollmentRequest request,
            HttpServletRequest httpServletRequest
    ) {
        EnrollmentResponse enrollmentResponse =
                enrollmentService.create(request, IpUtils.getClientIp(httpServletRequest));

        return ResponseData.success(
                enrollmentResponse,
                "Create successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getById(
            @PathVariable
            Long id ,
            HttpServletRequest httpServletRequest
    ){
        EnrollmentResponse enrollmentResponse = enrollmentService.getById(id, IpUtils.getClientIp(httpServletRequest));
        return ResponseData.success(
                enrollmentResponse,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAll(
            @PageableDefault(size = 10, sort = "createdAt")
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<EnrollmentResponse> page =
                enrollmentService.getAll(pageable,IpUtils.getClientIp(httpServletRequest));
        return ResponseData.successPaginate(
                page,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> updateById(
            @PathVariable
            Long id,
            @Valid
            @RequestBody EnrollmentUpdateRequest request,
            HttpServletRequest httpServletRequest
    ) {
        EnrollmentResponse response =
                enrollmentService.update(
                        id,
                        request,
                        IpUtils.getClientIp(httpServletRequest)
                );

        return ResponseData.success(
                response,
                "Update successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void  >> deleteById(
            @PathVariable
            Long id,
            HttpServletRequest httpServletRequest
    ) {
        enrollmentService.delete(id,IpUtils.getClientIp(httpServletRequest));

        return ResponseData.success(
                null,
                "Delete successfully",
                HttpStatus.OK
        );
    }


}
