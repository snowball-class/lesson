package snowball.lesson.controller;

import jakarta.servlet.http.HttpServletResponse;
import snowball.lesson.lesson.Lesson;
import snowball.lesson.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    /*
    *
    * */
    @GetMapping("/lesson/{id}")
    public Lesson getLessonDetails(@PathVariable()long lessonId, HttpServletResponse response){
        return lessonService.findLesson(lessonId);
    }

}
