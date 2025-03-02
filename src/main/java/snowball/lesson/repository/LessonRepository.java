package snowball.lesson.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import snowball.lesson.entity.lesson.Lesson;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Override
    @Query("SELECT l FROM Lesson l JOIN FETCH l.category WHERE l.lessonId = :id")
    Optional<Lesson> findById(@Param("id") Long id);

    @Query("SELECT l FROM Lesson l JOIN FETCH l.category WHERE l.tutor LIKE %:keyword% OR l.title LIKE %:keyword%")
    Page<Lesson> findByTutorOrTitleContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT l FROM Lesson l JOIN FETCH l.category WHERE l.category.categoryId = :categoryId")
    Page<Lesson> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}
