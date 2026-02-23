package BTEC.ASM.project.modules.academic.controller;


import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.academic.dto.request.SubjectRequest;
import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.SubjectResponse;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.service.SubjectService;
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
@RequestMapping("api/subjects")
@Tag(name = "Subject",description = "Subject management APIs")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SubjectRequest request,
                                    HttpServletRequest httpServletRequest){
        SubjectResponse subjectResponse = subjectService.create(request, IpUtils.getClientIp(httpServletRequest));
        return ResponseData.success(subjectResponse,
                "Create successfully",
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAll(
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<SubjectResponse> subjectResponsePage = subjectService.findAll(
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(subjectResponsePage,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("{subjectCode}")
    public ResponseEntity<?> findBySubjectCode(
            @PathVariable String subjectCode,
            HttpServletRequest httpServletRequest
    ){
        SubjectResponse subjectResponse = subjectService.findBySubjectCode(
                subjectCode,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.success(subjectResponse,
                "Find successfully",
                HttpStatus.OK
        );
    }
    @GetMapping("{subjectName}")
    public ResponseEntity<?> findBySubjectNameContainingIgnoreCase(
            @PathVariable String subjectName,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ){
        Page<SubjectResponse> subjectResponse = subjectService.findBySubjectNameContainingIgnoreCase(
                subjectName,
                pageable,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.successPaginate(subjectResponse,
                "Find successfully",
                HttpStatus.OK
        );
    }

    @PutMapping("/{subjectCode}")
    public ResponseEntity<?> updateBySubjectCode(
            @PathVariable String subjectCode,
            @Valid
            @RequestBody SubjectRequest request,
            HttpServletRequest httpServletRequest
    ) {
        SubjectResponse subjectResponse =
                subjectService.updateBySubjectCode(
                        subjectCode,
                        request,
                        IpUtils.getClientIp(httpServletRequest)
                );

        return ResponseData.success(
                subjectResponse,
                "Update successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("{subjectCode}")
    public ResponseEntity<?> deleteBySubjectCode(
            @PathVariable String subjectCode,
            HttpServletRequest httpServletRequest
    ){
        subjectService.deleteBySubjectCode(
                subjectCode,
                IpUtils.getClientIp(httpServletRequest)
        );
        return ResponseData.success(
                null,
                "Delete successfully",
                HttpStatus.OK
        );
    }
}
