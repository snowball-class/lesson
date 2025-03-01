package snowball.lesson.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import snowball.lesson.entity.lesson.Lesson;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Override
    @Query("SELECT l FROM Lesson l JOIN FETCH l.category WHERE l.lessonId = :id")
    Optional<Lesson> findById(@Param("id") Long id);

//    List<LessonResponse> getEventLessonList(long eventId);
//    List<LessonResponse> getLessonList(int categoryId);
//    List<LessonResponse> getSearchLesson(String keyword);
//
//    // 상세페이지
//    LessonDetailsResponse findById(long id);
//
//    // 마이페이지
//    List<GetMemberLessonDto> getMemberLessonList(UUID memberId);
//
//    // 이벤트 정보 업데이트
//    void bulkUpdateLessonsForEvent(long eventId, Integer discountRate, LocalDateTime discountStartDate, LocalDateTime discountFinishDate, List<Long> lessonIds);
}
