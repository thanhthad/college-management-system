package BTEC.ASM.project.modules.academic.controller;
import BTEC.ASM.project.common.response.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/demo")
public class DemoController {

    /* =========================
       1️⃣ SUCCESS THƯỜNG
    ========================= */
    @GetMapping("/success")
    public ResponseEntity<?> success() {

        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "Nguyen Van A");
        user.put("email", "a@gmail.com");

        return ResponseData.success(
                user,
                "Get user success",
                HttpStatus.OK
        );
    }

    /* =========================
       2️⃣ SUCCESS PAGINATE
    ========================= */
    @GetMapping("/paginate")
    public ResponseEntity<?> paginate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        // Fake list users
        List<User> users = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new User(i, "User " + i))
                .toList();

        Pageable pageable = PageRequest.of(page - 1, size);

        int start = Math.min((int) pageable.getOffset(), users.size());
        int end = Math.min(start + pageable.getPageSize(), users.size());

        Page<User> pageData = new PageImpl<>(
                users.subList(start, end),
                pageable,
                users.size()
        );

        Map<String, Object> additional = new HashMap<>();
        additional.put("role", "ADMIN");
        additional.put("can_delete", true);

        return ResponseData.successPaginate(
                pageData,
                additional,
                "Get users paginate success",
                HttpStatus.OK
        );
    }

    /* =========================
       3️⃣ FAIL
    ========================= */
    @GetMapping("/fail")
    public ResponseEntity<?> fail() {
        return ResponseData.fail(
                null,
                "Something went wrong",
                HttpStatus.BAD_REQUEST
        );
    }

    /* =========================
       4️⃣ EXCEPTION (TEST GLOBAL HANDLER)
    ========================= */
    @GetMapping("/exception")
    public ResponseEntity<?> exception() {
        int x = 1 / 0; // cố tình nổ
        return ResponseEntity.ok(x);
    }

    /* =========================
       DTO FAKE
    ========================= */
    @Data
    @AllArgsConstructor
    static class User {
        private int id;
        private String name;
    }
}

