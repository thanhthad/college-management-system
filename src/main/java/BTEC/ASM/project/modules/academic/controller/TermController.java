package BTEC.ASM.project.modules.academic.controller;

import BTEC.ASM.project.common.response.ResponseData;
import BTEC.ASM.project.modules.academic.dto.request.TermRequest;
import BTEC.ASM.project.modules.academic.service.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/terms")
@Tag(name = "Term",description = "Term management APIs")
@SecurityRequirement(name = "bearerAuth")
public class TermController {
    private final TermService termService;

    @GetMapping("/{id}")
    @Operation(summary = "get by id")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseData.success(termService.getById(id),"Get Term by id:" + id + " successfully", HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "create term")
    public ResponseEntity<?> create(@RequestBody TermRequest termRequest){
        return ResponseData.success(termService.create(termRequest),"Create term successfully",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update term")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TermRequest termRequest){
        return ResponseData.success(termService.update(id,termRequest),"Update term successfully",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete term")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return ResponseData.success(null,"Delete term successfully",HttpStatus.OK);
    }

}
