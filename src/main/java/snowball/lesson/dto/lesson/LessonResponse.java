package snowball.lesson.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class LessonResponse {
    private long lessonId;
    private String title;
    private int categoryId;
    private Integer price;
    private String thumbnailUrl;
}
