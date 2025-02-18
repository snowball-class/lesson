package snowball.lesson.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import snowball.lesson.dto.ApiResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponseEntity<ErrorDto>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        ErrorDto error = new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseEntity.fail(error));
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ApiResponseEntity<ErrorDto>> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e) {
        ErrorDto error = new ErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseEntity.fail(error));
    }

    @ExceptionHandler(LessonNotFoundException.class)
    public ResponseEntity<ApiResponseEntity<ErrorDto>> handleLessonNotFoundException(LessonNotFoundException e) {
        ErrorDto error = new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseEntity.fail(error));
    }
}