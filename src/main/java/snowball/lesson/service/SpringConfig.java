package snowball.lesson.service;

import snowball.lesson.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import snowball.lesson.repository.JdbcLessonRepository;
import snowball.lesson.repository.LessonRepository;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public LessonService lessonService() {
        return new LessonService(lessonRepository());
    }

    @Bean
    public LessonRepository lessonRepository(){
        return new JdbcLessonRepository(dataSource);
    }
}
