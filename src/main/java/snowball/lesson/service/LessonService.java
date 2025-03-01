package snowball.lesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.lesson.dto.lesson.LessonDetailsResponse;
import snowball.lesson.entity.lesson.Lesson;
import snowball.lesson.exception.ErrorCode;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.repository.LessonRepository;

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
    public LessonDetailsResponse getLessonDetails(Long lessonId) {
        return LessonDetailsResponse.from(getLessonById(lessonId));
    }


//    public List<LessonResponse> findLessonList(int categoryId) {
//        List<LessonResponse> result = lessonRepository.getLessonList(categoryId);
//        if (result.isEmpty()) {
//            throw new LessonNotFoundException(ErrorCode.LESSON_NOT_FOUND);
//        }
//        return result;
//    }
//
//    public List<LessonResponse> findEventLessonList(Long eventId) {
//        List<LessonResponse> result = lessonRepository.getEventLessonList(eventId);
//        if (result.isEmpty()) {
//            throw new LessonNotFoundException(ErrorCode.LESSON_NOT_FOUND);
//        }
//        return result;
//    }
//
//    public List<LessonResponse> searchLesson(String keyword) {
//        List<LessonResponse> result = lessonRepository.getSearchLesson(keyword);
//        if (result.isEmpty()) {
//            throw new LessonNotFoundException(ErrorCode.LESSON_NOT_FOUND);
//        }
//        return result;
//    }
//    public List<GetMemberLessonDto> findMemberLessonList(UUID memberId){
//        List<GetMemberLessonDto> result = lessonRepository.getMemberLessonList(memberId);
//        if(result.isEmpty()){
//            throw new LessonNotFoundException(ErrorCode.LESSON_NOT_FOUND);
//        }
//        return result;
//    }

//    @Transactional
//    public void applyEvent(LessonApplyEventRequest request) {
//        lessonRepository.bulkUpdateLessonsForEvent(request.eventId(),
//                request.discountRate(),
//                request.discountStartDate(), request.discountFinishDate(),
//                request.lessonIds());
//    }
//
//    @Transactional
//    public void deleteEvent(Long eventId) {
//        applyEvent(LessonApplyEventRequest.from(
//                0L, 0,
//                LocalDateTime.now(), LocalDateTime.now(),
//                findEventLessonList(eventId).stream()
//                        .map(LessonResponse::getLessonId)
//                        .collect(Collectors.toList())));
//    }
}
