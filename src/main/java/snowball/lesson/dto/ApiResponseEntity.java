package snowball.lesson.dto;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import snowball.lesson.exception.ErrorDto;

public record ApiResponseEntity<T>(
        HttpStatus status,
        boolean success,
        @Nullable T data,
       String message
) {

    public static <T> ApiResponseEntity<T> success(@Nullable final T data) {
        return new ApiResponseEntity<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ApiResponseEntity<T> fail(ErrorDto error) {
        return new ApiResponseEntity<>(HttpStatus.NOT_FOUND, false, null, error.message());
    }
}