package snowball.lesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import snowball.lesson.dto.ApiResponse;
import snowball.lesson.dto.lesson.LessonCreateRequest;
import snowball.lesson.dto.lesson.LessonResponse;
import snowball.lesson.dto.lesson.LessonUpdateRequest;
import snowball.lesson.service.LessonService;

@Tag(name = "강의 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @Operation(summary = "강의 생성")
    @PostMapping
    public ApiResponse<Long> createLesson(LessonCreateRequest request) {
        return ApiResponse.success(lessonService.createLesson(request));
    }

    @Operation(summary = "강의 ID로 강의 조회")
    @GetMapping("/{lessonId}")
    public ApiResponse<LessonResponse> getLessonResponse(@PathVariable("lessonId") Long lessonId) {
        return ApiResponse.success(lessonService.getLesson(lessonId));
    }

    @Operation(summary = "카테고리 ID로 강의 조회")
    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<LessonResponse>> getLessonList(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LessonResponse> data = lessonService.getLessonListByCategory(categoryId, page, size);
        return ApiResponse.success(data);
    }

    @Operation(summary = "강의 타이틀과 강사 이름에 포함된 키워드로 강의 조회")
    @GetMapping("/search/{keyword}")
    public ApiResponse<Page<LessonResponse>> getSearchLesson(
            @PathVariable String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LessonResponse> data = lessonService.getLessonListByKeyword(keyword, page, size);
        return ApiResponse.success(data);
    }

    @Operation(summary = "강의 수정")
    @PutMapping("/{lessonId}")
    public ApiResponse<LessonResponse> updateLesson(@PathVariable("lessonId") Long lessonId, LessonUpdateRequest request) {
        return ApiResponse.success(lessonService.updateLesson(lessonId, request));
    }
}
