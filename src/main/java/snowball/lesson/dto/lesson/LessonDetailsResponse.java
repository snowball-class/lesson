package snowball.lesson.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import snowball.lesson.entity.lesson.Lesson;

@Schema
public record LessonDetailsResponse(
        @Schema(description = "강의 고유키", example = "13")
        Long lessonId,
        @Schema(description = "강의 제목", example = "가장 쉽게 배우는 도커")
        String title,
        @Schema(description = "강사 이름", example = "얄팍한 코딩사전")
        String tutor,
        @Schema(description = "카테고리 고유키", example = "2")
        Long categoryId,
        @Schema(description = "카테고리 이름", example = "음악")
        String categoryName,
        @Schema(description = "강의 가격", example = "30000")
        Integer price,
        @Schema(description = "강의 내용 1", example = "이번에는 실전! \uD83D\uDC33 도커를 실제 프로젝트에 사용해봅시다.")
        String content1,
        @Schema(description = "강의 내용 2", example = "프론트와 백엔드, 데이터베이스로 구성되는 모듈들을도커로 컨테이너화하여 서비스를 구축하고 돌려보기로 해요! \uD83D\uDE00")
        String content2,
        @Schema(description = "썸네일 이미지 url")
        String thumbnailUrl
            /*
            float starRating;
            int netPrice;
            int salePrice;
             */
) {
    public static LessonDetailsResponse from(Lesson lesson) {
        return new LessonDetailsResponse(
                lesson.getLessonId(),
                lesson.getTitle(),
                lesson.getTutor(),
                lesson.getCategory().getCategoryId(),
                lesson.getCategory().getName(),
                lesson.getPrice(),
                lesson.getContent1(),
                lesson.getContent2(),
                lesson.getThumbnailUrl()
        );
    }
}
