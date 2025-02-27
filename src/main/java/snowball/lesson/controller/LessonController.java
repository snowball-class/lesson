package snowball.lesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import snowball.lesson.dto.*;
import snowball.lesson.lesson.LessonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Lesson")
public class LessonController {
    /* 스웨거 로컬 테스트 URL
    * http://localhost:8082/swagger-ui/index.html
    * */
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    /*
     *   강의 상세페이지 조회 - 강의 id를 받아 단건 조회
     *   request     : Lesson Id
     *   response    : GetLessonDetailsDto
     * */
    @GetMapping("/details/{id}")
    public ApiResponse<GetLessonDetailsDto> getLessonDetails(@PathVariable long id) {
        GetLessonDetailsDto data = lessonService.findLesson(id);
        return ApiResponse.success(data);
    }

    /*
     *   강의 카테고리 조회 - 카테고리 id를 받아 리스트 조회
     *   request     : Category Id
     *   response    : GetLessonDto List
     * */
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<GetLessonDto>> getLessonList(@PathVariable int categoryId) {
        List<GetLessonDto> data = lessonService.findLessonList(categoryId);
        return ApiResponse.success(data);
    }

    /*
     *   강의 카테고리 조회 - 이벤트 id를 받아 리스트 조회
     *   request     : event Id
     *   response    : GetLessonDto List
     * */
    @GetMapping("/event/{eventId}")
    public ApiResponse<List<GetLessonDto>> getEventLessonList(@PathVariable() Long eventId) {
        List<GetLessonDto> data = lessonService.findEventLessonList(eventId);
        return ApiResponse.success(data);
    }

    @GetMapping("/search/{keyword}")
    public ApiResponse<List<GetLessonDto>> getSearchLesson(@PathVariable String keyword) {
        List<GetLessonDto> data = lessonService.searchLesson(keyword);
        return ApiResponse.success(data);
    }

    @GetMapping("/member/{memberId}")
    public ApiResponse<List<GetMemberLessonDto>> getMemberLessonList(@PathVariable UUID memberId) {
        List<GetMemberLessonDto> data = lessonService.findMemberLessonList(memberId);
        return ApiResponse.success(data);
    }


    /*
     *   이벤트 정보 갱신 - lesson id를 받아 이벤트 정보 bulk update
     *   request     : ApplyEventToLessonRequest
     *
     * */
    @Operation(hidden = true)
    @PutMapping("/event")
    public ApiResponse applyEventToLesson(@Valid @RequestBody ApplyEventToLessonRequest request) {
        lessonService.applyEvent(request);
        return ApiResponse.success("success");
    }

    /*
     *   이벤트 정보 삭제 - eventId=0, discountRate=0 으로 bulk update
     *   request     : ApplyEventToLessonRequest
     *
     * */
    @Operation(hidden = true)
    @DeleteMapping("/event/{eventId}")
    public ApiResponse deleteEventFromLesson(@PathVariable("eventId") Long eventId) {
        lessonService.deleteEvent(eventId);
        return ApiResponse.success("success");
    }

}
