package snowball.lesson.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.lesson.dto.ApplyEventToLessonRequest;
import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;
import snowball.lesson.dto.GetMemberLessonDto;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.repository.LessonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public GetLessonDetailsDto findLesson(Long lessonId) {
        return lessonRepository.findById(lessonId);
    }


    public List<GetLessonDto> findLessonList(int categoryId) {
        List<GetLessonDto> result = lessonRepository.getLessonList(categoryId);
        if (result.isEmpty()) {
            throw new LessonNotFoundException("This event has not lesson");
        }
        return result;
    }

    public List<GetLessonDto> findEventLessonList(Long eventId) {
        List<GetLessonDto> result = lessonRepository.getEventLessonList(eventId);
        if (result.isEmpty()) {
            throw new LessonNotFoundException("This event has not lesson");
        }
        return result;
    }

    public List<GetLessonDto> searchLesson(String keyword) {
        List<GetLessonDto> result = lessonRepository.getSearchLesson(keyword);
        if (result.isEmpty()) {
            throw new LessonNotFoundException("No results found");
        }
        return result;
    }
    public List<GetMemberLessonDto> findMemberLessonList(UUID memberId){
        List<GetMemberLessonDto> result = lessonRepository.getMemberLessonList(memberId);
        if(result.isEmpty()){
            throw new LessonNotFoundException("No results found");
        }
        return result;
    }

    @Transactional
    public void applyEvent(ApplyEventToLessonRequest request) {
        lessonRepository.bulkUpdateLessonsForEvent(request.eventId(),
                request.discountRate(),
                request.discountStartDate(), request.discountFinishDate(),
                request.lessonIds());
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        applyEvent(ApplyEventToLessonRequest.from(
                0L, 0,
                LocalDateTime.now(), LocalDateTime.now(),
                findEventLessonList(eventId).stream()
                        .map(GetLessonDto::getLessonId)
                        .collect(Collectors.toList())));
    }
}
