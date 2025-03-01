package snowball.lesson.dto.lesson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LessonMemberResponse {
    // 삭제 예정 - view 서비스로 이동
    private long lessonId;
    private String title;
    private String tutor;
    private String thumbnail;
    private int categoryId;
    private int isEnrolled;
    private LocalDateTime startDate;
}
