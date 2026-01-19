package mat.shopProject.shop.project.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<String> details
) {
    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }
}
