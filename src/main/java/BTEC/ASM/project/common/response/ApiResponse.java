package BTEC.ASM.project.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "status_code",
        "data",
        "pagination",
        "additional_data"
})
public class ApiResponse<T> {
    private String message;
    private int status_code;
    private T data;
    private Object pagination;
    private Object additional_data;
}
