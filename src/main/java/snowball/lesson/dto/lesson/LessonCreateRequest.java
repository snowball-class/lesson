package snowball.lesson.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record LessonCreateRequest(
        @NotBlank(message = "강의 제목을 입력해 주세요.")
        @Size(min = 2, max = 100, message = "100자 이내로 입력해 주세요.")
        @Schema(description = "강의 제목", example = "가장 쉽게 배우는 쿠버네티스!")
        String title,
        @NotBlank(message = "강사 이름을 입력해 주세요.")
        @Size(min = 2, max = 20, message = "100자 이내로 입력해 주세요.")
        @Schema(description = "강사 이름", example = "Jerry")
        String tutor,
        @NotNull(message = "카테고리 ID를 입력해 주세요.")
        @Schema(description = "카테고리 고유키", example = "1")
        Long categoryId,
        @NotNull(message = "강의 가격을 입력해 주세요.")
        @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
        @Max(value = 10000000, message = "가격은 천만원을 넘길 수 없습니다.")
        @Schema(description = "강의 가격", example = "59900")
        Integer price,
        @NotBlank(message = "강의 내용1을 입력해 주세요.")
        @Size(min = 2, max = 700, message = "700자 이내로 입력해 주세요.")
        @Schema(description = "강의 내용 1", example = "이번에는 실전! 쿠버네티스를 실제 프로젝트에 사용해봅시다.")
        String content1,
        @NotBlank(message = "강의 내용2를 입력해 주세요.")
        @Size(min = 2, max = 700, message = "700자 이내로 입력해 주세요.")
        @Schema(description = "강의 내용 2", example = "쿠버네티스의 다양한 기능들을 활용해 보아요! \uD83D\uDE00")
        String content2,
        @NotBlank(message = "썸네일 url을 입력해 주세요.")
        @Pattern(regexp = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$", message = "올바른 URL 형식이어야 합니다.")
        @Schema(description = "썸네일 url", example = "https://snowball-bucket.s3.ap-northeast-2.amazonaws.com/5906231e-4k8s-inflearn.png")
        String thumbnailUrl,
        @NotBlank(message = "강의 영상 url을 입력해 주세요.")
        @Pattern(regexp = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$", message = "올바른 URL 형식이어야 합니다.")
        @Schema(description = "강의 영상 url", example = "https://www.youtube.com/watch?v=eRfHp16qJq8")
        String videoUrl
) {
}
