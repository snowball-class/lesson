package snowball.lesson.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snowball.lesson.repository.LessonRepository;


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
}
