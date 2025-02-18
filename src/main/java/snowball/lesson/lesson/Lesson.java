package snowball.lesson.lesson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snowball.lesson.dto.GetLessonDetailsDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Lesson {
    private long lessonId;
    private String title;
    private String tutor;
    private int categoryId;
    private int price;
    private String content1;
    private String content2;
    private int thumbnail;
    private LocalDateTime registerDate;
    private int eventId;
    private int discountRate;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountFinishDate;

//    public GetLessonDetailsDto toGetLessonDetailsDto(){
//        GetLessonDetailsDto dto = new GetLessonDetailsDto();
//        dto.setLessonId(this.lessonId);
//
//        return dto;
//    }
}
