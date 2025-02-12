package snowball.lesson.repository;


import snowball.lesson.lesson.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    // 메인페이지
    List<Lesson> getEventLessonList(int eventId);
    List<Lesson> getLessonList(int categoryId);

    // 상세페이지
    Lesson findById(long id);

}
