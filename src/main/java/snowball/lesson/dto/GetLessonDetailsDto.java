package snowball.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@Getter
@Setter
public class GetLessonDetailsDto {

    private long lessonId;
    private String title;
    private String tutor;
    private int categoryId;
    private String categoryName;
    private float starRating;
    private int netPrice;
    private int salePrice;
    private String content1;
    private String content2;
    private String thumbnail;
    private long eventId;
    private int discountRate;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountFinishDate;
}
