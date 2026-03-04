package BTEC.ASM.project.modules.academic.controller;

import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.dto.response.TermResponse;
import BTEC.ASM.project.modules.academic.service.TermService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RestController
@AllArgsConstructor
@RequestMapping("/api/terms")
@Tag(name = "Term", description = "Term management APIs")
public class TermController {

    private final TermService termService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(
            @Valid
            @RequestBody TermRequest request,
            HttpServletRequest httpServletRequest
    ) {
        TermResponse termResponse =
                termService.create(request, IpUtils.getClientIp(httpServletRequest));

        return ResponseData.success(
                termResponse,
                "Create successfully",
                HttpStatus.CREATED
        );
    }

    // GET BY CODE
    @GetMapping("/{termCode}")
    public ResponseEntity<?> getByTermCode(
            @PathVariable String termCode,
            HttpServletRequest httpServletRequest
    ) {
        TermResponse termResponse =
                termService.getByTermCode(termCode, IpUtils.getClientIp(httpServletRequest));

        return ResponseData.success(
                termResponse,
                "Find successfully",
                HttpStatus.OK
        );
    }

    // FIND BY START DATE AFTER
    @GetMapping("/search/start-date-after")
    public ResponseEntity<?> getByStartDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        Page<TermResponse> pages =
                termService.getByStartDate(
                        date,
                        pageable,
                        IpUtils.getClientIp(httpServletRequest)
                );

        return ResponseData.successPaginate(
                pages,
                "Find successfully",
                HttpStatus.OK
        );
    }

    // FIND ACTIVE TERMS
    @GetMapping("/search/active")
    public ResponseEntity<?> getActiveTerms(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        Page<TermResponse> pages =
                termService.getByStartDateAndEndDate(
                        from,
                        to,
                        pageable,
                        IpUtils.getClientIp(httpServletRequest)
                );

        return ResponseData.successPaginate(
                pages,
                "Find successfully",
                HttpStatus.OK
        );
    }

    // FIND ALL
    @GetMapping
    public ResponseEntity<?> getAll(
            Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
        Page<TermResponse> pages =
                termService.getAll(pageable, IpUtils.getClientIp(httpServletRequest));

        return ResponseData.successPaginate(
                pages,
                "Find successfully",
                HttpStatus.OK
        );
    }

    // UPDATE BY CODE
    @PutMapping("/{termCode}")
    public ResponseEntity<?> updateByTermCode(
            @PathVariable String termCode,
            @Valid
            @RequestBody TermRequest request,
            HttpServletRequest httpServletRequest
    ) {
        TermResponse response =
                termService.updateByTermCode(
                        termCode,
                        request,
                        IpUtils.getClientIp(httpServletRequest)
                );

        return ResponseData.success(
                response,
                "Update successfully",
                HttpStatus.OK
        );
    }

    // DELETE BY CODE
    @DeleteMapping("/{termCode}")
    public ResponseEntity<?> deleteByTermCode(
            @PathVariable String termCode,
            HttpServletRequest httpServletRequest
    ) {
        termService.deleteByTermCode(
                termCode,
                IpUtils.getClientIp(httpServletRequest)
        );

        return ResponseData.success(
                null,
                "Delete successfully",
                HttpStatus.OK
        );
    }
}

