package BTEC.ASM.project.modules.academic.controller;

import BTEC.ASM.project.common.response.ApiResponse;
import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.common.utils.IpUtils;
import BTEC.ASM.project.modules.academic.dto.request.AdminOfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingFilter;
import BTEC.ASM.project.modules.academic.dto.request.OfferingRequest;
import BTEC.ASM.project.modules.academic.dto.response.OfferingResponse;
import BTEC.ASM.project.modules.academic.service.OfferingService;
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
@RequestMapping("/api/offerings")
@Tag(name = "Offering",description = "Offering management APIs")
public class OfferingController {

    private final OfferingService offeringService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OfferingResponse>>> searchByCode(
            @Valid
            OfferingFilter filter,
            Pageable pageable,
            HttpServletRequest request
    ) {
        return ResponseData.success(
                offeringService.filterOffering(
                        filter,
                        pageable,
                        IpUtils.getClientIp(request)
                ),
                "Find successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<Page<OfferingResponse>>> searchById(
            @Valid
            AdminOfferingFilter filter,
            Pageable pageable,
            HttpServletRequest request
    ) {
        return ResponseData.success(
                offeringService.filterOfferingAdmin(
                        filter,
                        pageable,
                        IpUtils.getClientIp(request)
                ),
                "Find successfully",
                HttpStatus.OK
        );
    }
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody OfferingRequest request,
                                    HttpServletRequest httpServletRequest ){
        OfferingResponse response = offeringService.create(request,IpUtils.getClientIp(httpServletRequest));
        return ResponseData.success(
                response,
                "Create successfully",
                HttpStatus.OK
        );
    }

    @PutMapping("{offeringId}")
    public ResponseEntity<?> updateByOfferingId(
            @PathVariable Long offeringId,
            @Valid
            @RequestBody OfferingRequest offeringRequest,
            HttpServletRequest httpServletRequest
    ){
        OfferingResponse offeringResponse = offeringService.update(offeringId,offeringRequest,IpUtils.getClientIp(httpServletRequest));
        return ResponseData.success(
                offeringResponse,
                "Update successfully",
                HttpStatus.OK
        );
    }


    @DeleteMapping("{offeringId}")
    public ResponseEntity<?> deleteByOfferingId(
            @PathVariable Long offeringId,
            HttpServletRequest httpServletRequest
    ){
        offeringService.delete(offeringId,IpUtils.getClientIp(httpServletRequest));
        return ResponseData.success(
                null,
                "Delete successfully",
                HttpStatus.OK
        );
    }

}
