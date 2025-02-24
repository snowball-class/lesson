package snowball.lesson.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetMemberLessonDto {
    private long lessonId;
    private String title;
    private String tutor;
    private String thumbnail;
    private int categoryId;
    private int isEnrolled;
    private LocalDateTime startDate;
}
