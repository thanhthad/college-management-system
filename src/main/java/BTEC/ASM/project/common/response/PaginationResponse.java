package BTEC.ASM.project.common.response;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PaginationResponse {
    public static <T> Map<String, Object> fromPage(Page<T> page) {
        Map<String, Object> pagination = new HashMap<>();

        pagination.put("current_page", page.getNumber() + 1);
        pagination.put("from", page.getNumber() * page.getSize() + 1);
        pagination.put("last_page", page.getTotalPages());
        pagination.put("per_page", page.getSize());
        pagination.put("to",
                page.getNumber() * page.getSize() + page.getNumberOfElements());
        pagination.put("total", page.getTotalElements());

        return pagination;
    }
}
