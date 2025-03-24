package snowball.lesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import snowball.lesson.dto.ApiResponse;
import snowball.lesson.dto.lesson.LessonCreateRequest;
import snowball.lesson.dto.lesson.LessonResponse;
import snowball.lesson.dto.lesson.LessonUpdateRequest;
import snowball.lesson.service.LessonService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public ApiResponse<LessonResponse> getLessonByLessonId(@PathVariable("lessonId") Long lessonId) {
        return ApiResponse.success(lessonService.getLesson(lessonId));
    }

    @Operation(summary = "여러 강의 ID로 강의 벌크 조회")
    @GetMapping("/bulk")
    public ApiResponse<List<LessonResponse>> getBulkLessons(
            @Parameter(
                    description = "조회할 강의 ID 목록 (쉼표로 구분)",
                    example = "95,110,8,15,23,150,48,51,69",
                    required = true
            )
            @RequestParam("ids") String lessonIds
    ) {
        List<Long> ids = Arrays.stream(lessonIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return ApiResponse.success(lessonService.getBulkLessons(ids));
    }

    @Operation(summary = "카테고리 ID로 강의 조회")
    @Parameter(name = "type", description = "Type 종류: PRICE_ASC, PRICE_DESC, STUDENT_DESC, RATING_DESC", required = true)
    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<LessonResponse>> getLessonListByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "PRICE_ASC") String type) {
        Page<LessonResponse> data = lessonService.getLessonListByCategory(categoryId, page, size, type);
        return ApiResponse.success(data);
    }

    @Operation(summary = "강의 타이틀과 강사 이름에 포함된 키워드로 강의 조회")
    @Parameter(name = "type", description = "Type 종류: PRICE_ASC, PRICE_DESC, STUDENT_DESC, RATING_DESC", required = true)
    @GetMapping("/search/{keyword}")
    public ApiResponse<Page<LessonResponse>> getLessonListByKeyword(
            @PathVariable String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "PRICE_ASC") String type) {
        Page<LessonResponse> data = lessonService.getLessonListByKeyword(keyword, page, size, type);
        return ApiResponse.success(data);
    }

    @Operation(summary = "강의 수정")
    @PutMapping("/{lessonId}")
    public ApiResponse<Long> updateLesson(@PathVariable("lessonId") Long lessonId, LessonUpdateRequest request) {
        return ApiResponse.success(lessonService.updateLesson(lessonId, request));
    }
}
