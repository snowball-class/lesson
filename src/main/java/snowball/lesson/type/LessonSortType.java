package snowball.lesson.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LessonSortType {
    PRICE_ASC("낮은가격"), // 낮은 가격순
    PRICE_DESC("높은가격"), // 높은 가격순
    STUDENT_DESC("높은수강자"), // 수강자 높은순
    RATING_DESC("높은별점"); // 별점 높은순

    private String name;

    @JsonCreator
    public static LessonSortType of(final String parameter) {
        String state = parameter.toUpperCase();
        return Arrays.stream(values())
                .filter(type -> type.toString().equals(state) || type.getName().equals(state)) // toString: PRICE_ASC... / name: 낮은가격...
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 클래스 정렬 기준입니다."));
    }
}
