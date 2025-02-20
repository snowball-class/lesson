package snowball.lesson.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.lesson.dto.ApplyEventToLessonRequest;
import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.repository.LessonRepository;

import java.util.List;

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

    public List<GetLessonDto> findEventLessonList(int eventId) {
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

    @Transactional
    public void applyEvent(ApplyEventToLessonRequest request) {
        lessonRepository.bulkUpdateLessonsForEvent(request.eventId(),
                request.discountRate(),
                request.discountStartDate(), request.discountFinishDate(),
                request.lessonIds());
    }
}
