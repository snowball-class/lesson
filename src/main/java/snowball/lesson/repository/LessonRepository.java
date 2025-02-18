package snowball.lesson.repository;


import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;

import java.util.List;

public interface LessonRepository {
    // 메인페이지
    List<GetLessonDto> getEventLessonList(int eventId);
    List<GetLessonDto> getLessonList(int categoryId);
    List<GetLessonDto> getSearchLesson(String keyword);

    // 상세페이지
    GetLessonDetailsDto findById(long id);

}
