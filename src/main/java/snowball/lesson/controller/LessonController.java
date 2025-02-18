package snowball.lesson.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import snowball.lesson.dto.ApiResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;
import snowball.lesson.exception.ErrorDto;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.lesson.Lesson;
import snowball.lesson.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/Lesson")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    /*
    *   강의 상세페이지 조회 - 강의 id를 받아 단건 조회
    *   request     : Lesson Id
    *   response    : GetLessonDetailsDto
    * */
    @GetMapping("/details/{id}")
    public ApiResponseEntity<GetLessonDetailsDto> getLessonDetails(@PathVariable long id){
        GetLessonDetailsDto data = lessonService.findLesson(id);
        return ApiResponseEntity.success(data);
    }

    /*
     *   강의 카테고리 조회 - 카테고리 id를 받아 리스트 조회
     *   request     : Category Id
     *   response    : GetLessonDto List
     * */
    @GetMapping("/category/{categoryId}")
    public ApiResponseEntity<List<GetLessonDto>> getLessonList(@PathVariable int categoryId){
        List<GetLessonDto> data = lessonService.findLessonList(categoryId);
        return ApiResponseEntity.success(data);
    }

    /*
     *   강의 카테고리 조회 - 이벤트 id를 받아 리스트 조회
     *   request     : event Id
     *   response    : GetLessonDto List
     * */
    @GetMapping("/event/{eventId}")
    public ApiResponseEntity<List<GetLessonDto>> getEventLessonList(@PathVariable()int eventId){
        List<GetLessonDto> data = lessonService.findEventLessonList(eventId);
        return ApiResponseEntity.success(data);
    }

    @GetMapping("/search/{keyword}")
    public ApiResponseEntity<List<GetLessonDto>> getSearchLesson(@PathVariable String keyword){
        List<GetLessonDto> data = lessonService.searchLesson(keyword);
        return ApiResponseEntity.success(data);
    }

}
