package snowball.lesson.exception;

import lombok.Getter;

@Getter
public class LessonNotFoundException extends ServiceException {

    public LessonNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public LessonNotFoundException(String message) {
        super(ErrorCode.ENTITY_NOT_FOUND, message);
    }

    public LessonNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}