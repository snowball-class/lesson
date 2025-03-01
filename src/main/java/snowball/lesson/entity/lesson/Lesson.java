package snowball.lesson.entity.lesson;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
    @Column(length = 700)
    private String content2;
    private String thumbnailUrl;
    private String videoUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
