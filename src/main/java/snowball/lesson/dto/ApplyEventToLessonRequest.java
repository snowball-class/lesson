package snowball.lesson.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ApplyEventToLessonRequest(
        @Schema(description = "이벤트 고유키", example = "1")
        Long eventId,
        @NotNull(message = "할인율을 입력해 주세요.")
        @Min(value = 1, message = "할인율은 1 이상이어야 합니다.")
        @Max(value = 100, message = "할인율은 100%를 넘길 수 없습니다.")
        @Schema(description = "이벤트 할인율", example = "25")
        Integer discountRate,
        @NotNull(message = "할인 시작 시간을 입력해 주세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 할인 시작 시간", example = "2025-11-24T12:00:00")
        LocalDateTime discountStartDate,
        @NotNull(message = "할인 종료 시간을 입력해 주세요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 할인 종료 시간", example = "2025-12-01T12:00:00")
        LocalDateTime discountFinishDate,
        @NotNull(message = "할인 이벤트에 추가할 클래스를 하나 이상 입력해 주세요.")
        @Size(min = 1, message = "할인 이벤트는 하나 이상의 클래스를 포함해야 합니다.")
        @Schema(description = "이벤트 할인 적용할 클래스 목록 (id)", example = "[1, 15, 6, 7, 2]")
        List<Long> lessonIds
) {
}
