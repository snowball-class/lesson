package snowball.lesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import snowball.lesson.dto.ApiResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.lesson.Lesson;
import snowball.lesson.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    /*
    *   강의 상세페이지 조회 - 강의 id를 받아 단건 조회
    *   request     : Lesson Id
    *   response    : Lesson Object
    * */
    @GetMapping("/lesson/details/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lessons retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Lessons not found")
    })
    public ApiResponseEntity<GetLessonDetailsDto> getLessonDetails(@PathVariable()long lessonId){
        GetLessonDetailsDto data = lessonService.findLesson(lessonId);
        return ApiResponseEntity.success(data);
    }

    /*
     *   강의 카테고리 조회 - 카테고리 id를 받아 리스트 조회
     *   request     : Category Id
     *   response    : Lesson Object List
     * */
    @GetMapping("/lesson/category/{categoryId}")
    public List<Lesson> getLessonList(@PathVariable()int categoryId, HttpServletResponse response){
        return lessonService.findLessonList(categoryId);
    }

    /*
     *   강의 카테고리 조회 - 카테고리 id를 받아 리스트 조회
     *   request     : Category Id
     *   response    : Lesson Object List
     * */
    @GetMapping("/lesson/event/{eventId}")
    public List<Lesson> getEventLessonList(@PathVariable()int eventId, HttpServletResponse response){
        return lessonService.findEventLessonList(eventId);
    }



}
