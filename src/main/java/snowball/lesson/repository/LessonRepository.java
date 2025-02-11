package snowball.lesson.repository;


import snowball.lesson.lesson.Lesson;

import java.util.List;

public interface LessonRepository {
    // 메인페이지
    List<Lesson> getBannerList();
    List<Lesson> getLessonList();


    // 카테고리

    // 상세페이지
    Lesson findById(long id);
    void addCart(long id);
    void delCart(long id);


}
