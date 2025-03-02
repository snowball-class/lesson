package snowball.lesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.lesson.dto.lesson.LessonResponse;
import snowball.lesson.entity.lesson.Lesson;
import snowball.lesson.exception.ErrorCode;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.repository.LessonRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Transactional(readOnly = true)
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException(ErrorCode.LESSON_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public LessonResponse getLesson(Long lessonId) {
        return LessonResponse.from(getLessonById(lessonId));
    }

    @Transactional(readOnly = true)
    public Page<LessonResponse> getLessonListByCategory(Long categoryId, int page, int size) {
        return lessonRepository.findAllByCategoryId(categoryId, PageRequest.of(page, size))
                .map(LessonResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<LessonResponse> getLessonListByKeyword(String keyword, int page, int size) {
        return lessonRepository.findByTutorOrTitleContaining(keyword, PageRequest.of(page, size))
                .map(LessonResponse::from);
    }

}
