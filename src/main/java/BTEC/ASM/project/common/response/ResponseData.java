package BTEC.ASM.project.common.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseData {
    public static <T> ResponseEntity<ApiResponse<T>> success(
            T data,
            String message,
            HttpStatus status
    ) {
        ApiResponse<T> res = new ApiResponse<>();
        res.setMessage(message);
        res.setStatus_code(status.value());
        res.setData(data);

        return new ResponseEntity<>(res, status);
    }

    public static <T> ResponseEntity<ApiResponse<Object>> successPaginate(
            Page<T> page,
            Object additionalData,
            String message,
            HttpStatus status
    ) {
        ApiResponse<Object> res = new ApiResponse<>();
        res.setMessage(message);
        res.setStatus_code(status.value());
        res.setData(page.getContent());
        res.setPagination(PaginationResponse.fromPage(page));
        res.setAdditional_data(additionalData);

        return new ResponseEntity<>(res, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(
            T data,
            String message,
            HttpStatus status
    ) {
        ApiResponse<T> res = new ApiResponse<>();
        res.setMessage(message);
        res.setStatus_code(status.value());
        res.setData(data);

        return new ResponseEntity<>(res, status);
    }
}