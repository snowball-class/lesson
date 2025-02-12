package snowball.lesson.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snowball.lesson.repository.LessonRepository;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository){
        this.lessonRepository = lessonRepository;
    }

    public Lesson findLesson(Long lessonId){
        return lessonRepository.findById(lessonId);
    }

    public List<Lesson> findLessonList(int categoryId){
        return lessonRepository.getLessonList(categoryId);
    }

    public List<Lesson> findEventLessonList(int eventId){
        return lessonRepository.getEventLessonList(eventId);
    }
}
