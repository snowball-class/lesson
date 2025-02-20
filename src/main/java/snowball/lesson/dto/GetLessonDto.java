package snowball.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@Getter
@Setter
public class GetLessonDto {
    private long lessonId;
    private String title;
    private int categoryId;
    private int netPrice;
    private int salePrice;
    private String thumbnail;
    private long eventId;
    private int discountRate;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountFinishDate;
}
