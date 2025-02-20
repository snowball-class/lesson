package snowball.lesson.repository;


import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository {
    // 메인페이지
    List<GetLessonDto> getEventLessonList(long eventId);
    List<GetLessonDto> getLessonList(int categoryId);
    List<GetLessonDto> getSearchLesson(String keyword);

    // 상세페이지
    GetLessonDetailsDto findById(long id);
    
    // 이벤트 정보 업데이트
    void bulkUpdateLessonsForEvent(long eventId, Integer discountRate, LocalDateTime discountStartDate, LocalDateTime discountFinishDate, List<Long> lessonIds);
}
