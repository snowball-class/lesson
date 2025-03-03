package snowball.lesson.entity.lesson;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import snowball.lesson.dto.lesson.LessonCreateRequest;
import snowball.lesson.dto.lesson.LessonUpdateRequest;
import snowball.lesson.entity.category.Category;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 20)
    private String tutor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false, length = 700)
    private String content1;
    @Column(nullable = false, length = 700)
    private String content2;
    @Column(nullable = false)
    private String thumbnailUrl;
    @Column(nullable = false)
    private String videoUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    private Lesson(String title, String tutor, Category category, Integer price, String content1, String content2, String thumbnailUrl, String videoUrl) {
        this.title = title;
        this.tutor = tutor;
        this.category = category;
        this.price = price;
        this.content1 = content1;
        this.content2 = content2;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
    }

    public static Lesson from(LessonCreateRequest request, Category category) {
        return Lesson.builder()
                .title(request.title())
                .tutor(request.tutor())
                .category(category)
                .price(request.price())
                .content1(request.content1())
                .content2(request.content2())
                .thumbnailUrl(request.thumbnailUrl())
                .videoUrl(request.videoUrl())
                .build();
    }

    public Long update(LessonUpdateRequest request, Category category) {
        this.title = request.title();
        this.category = category;
        this.content1 = request.content1();
        this.content2 = request.content2();
        this.thumbnailUrl = request.thumbnailUrl();
        this.videoUrl = request.videoUrl();
        return getLessonId();
    }

}
