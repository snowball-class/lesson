package snowball.lesson.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snowball.lesson.dto.ApiResponse;
import snowball.lesson.dto.lesson.LessonDetailsResponse;
import snowball.lesson.service.LessonService;

@Tag(name = "강의 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/{lessonId}/details")
    public ApiResponse<LessonDetailsResponse> getLessonDetails(@PathVariable("lessonId") Long lessonId) {
        return ApiResponse.success(lessonService.getLessonDetails(lessonId));
    }

//    /*
//     *   강의 카테고리 조회 - 카테고리 id를 받아 리스트 조회
//     *   request     : Category Id
//     *   response    : GetLessonDto List
//     * */
//    @GetMapping("/category/{categoryId}")
//    public ApiResponse<List<LessonResponse>> getLessonList(@PathVariable Integer categoryId) {
//        List<LessonResponse> data = lessonService.findLessonList(categoryId);
//        return ApiResponse.success(data);
//    }
//
//    /*
//     *   강의 카테고리 조회 - 이벤트 id를 받아 리스트 조회
//     *   request     : event Id
//     *   response    : GetLessonDto List
//     * */
//    @GetMapping("/event/{eventId}")
//    public ApiResponse<List<LessonResponse>> getEventLessonList(@PathVariable() Long eventId) {
//        List<LessonResponse> data = lessonService.findEventLessonList(eventId);
//        return ApiResponse.success(data);
//    }
//
//    @GetMapping("/search/{keyword}")
//    public ApiResponse<List<LessonResponse>> getSearchLesson(@PathVariable String keyword) {
//        List<LessonResponse> data = lessonService.searchLesson(keyword);
//        return ApiResponse.success(data);
//    }
//
//    @GetMapping("/member/{memberId}")
//    public ApiResponse<List<GetMemberLessonDto>> getMemberLessonList(@PathVariable UUID memberId) {
//        List<GetMemberLessonDto> data = lessonService.findMemberLessonList(memberId);
//        return ApiResponse.success(data);
//    }


//    /*
//     *   이벤트 정보 갱신 - lesson id를 받아 이벤트 정보 bulk update
//     *   request     : ApplyEventToLessonRequest
//     *
//     * */
//    @Operation(hidden = true)
//    @PutMapping("/event")
//    public ApiResponse applyEventToLesson(@Valid @RequestBody LessonApplyEventRequest request) {
//        lessonService.applyEvent(request);
//        return ApiResponse.success("success");
//    }
//
//    /*
//     *   이벤트 정보 삭제 - eventId=0, discountRate=0 으로 bulk update
//     *   request     : ApplyEventToLessonRequest
//     *
//     * */
//    @Operation(hidden = true)
//    @DeleteMapping("/event/{eventId}")
//    public ApiResponse deleteEventFromLesson(@PathVariable("eventId") Long eventId) {
//        lessonService.deleteEvent(eventId);
//        return ApiResponse.success("success");
//    }

}
