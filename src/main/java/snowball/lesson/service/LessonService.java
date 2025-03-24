package snowball.lesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.lesson.dto.lesson.LessonCreateRequest;
import snowball.lesson.dto.lesson.LessonResponse;
import snowball.lesson.dto.lesson.LessonUpdateRequest;
import snowball.lesson.entity.category.Category;
import snowball.lesson.entity.lesson.Lesson;
import snowball.lesson.exception.ErrorCode;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.repository.LessonRepository;
import snowball.lesson.type.LessonSortType;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CategoryService categoryService;

    @Transactional
    public Long createLesson(LessonCreateRequest request) {
        Category category = categoryService.getCategoryById(request.categoryId());
        Lesson lesson = Lesson.from(request, category);
        try {
            lessonRepository.saveAndFlush(lesson);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("입력값이 잘못되었습니다: " + e.getMessage());
        }
        return lesson.getLessonId();
    }

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
    public List<LessonResponse> getBulkLessons(List<Long> lessonIds) {
        List<Lesson> lessons = lessonRepository.findByLessonIdIn(lessonIds);
        return lessons.stream()
                .map(LessonResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<LessonResponse> getLessonListByCategory(Long categoryId, int page, int size, String type) {
        Pageable pageable = PageRequest.of(page, size, getLessonSort(type));
        return lessonRepository.findAllByCategoryId(categoryId, pageable)
                .map(LessonResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<LessonResponse> getLessonListByKeyword(String keyword, int page, int size, String type) {
        Pageable pageable = PageRequest.of(page, size, getLessonSort(type));
        return lessonRepository.findByTutorOrTitleContaining(keyword, pageable)
                .map(LessonResponse::from);
    }

    @Transactional
    public Long updateLesson(Long lessonId, LessonUpdateRequest request) {
        Category category = categoryService.getCategoryById(request.categoryId());
        Lesson lesson = getLessonById(lessonId);
        return lesson.update(request, category);
    }

    public Sort getLessonSort(String type) {
        LessonSortType sortType = LessonSortType.of(type);
        Sort sort;
        if (sortType == LessonSortType.PRICE_ASC) {
            sort = Sort.by("price").ascending();
        } else if (sortType == LessonSortType.PRICE_DESC) {
            sort = Sort.by("price").descending();
        } else {
            throw new IllegalArgumentException("아직 구현되지 않은 정렬 기준입니다.");
        }
        return sort;
    }
}
