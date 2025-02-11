package snowball.lesson.repository;

import snowball.lesson.lesson.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcLessonRepository implements LessonRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLessonRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Lesson> getBannerList() {
        return List.of();
    }

    @Override
    public List<Lesson> getLessonList() {
        return List.of();
    }

    @Override
    public Lesson findById(long id) {
        Lesson result = jdbcTemplate.queryForObject("select * from lesson where lnum = ?", lessonRowMapper(), id);
        return result;
    }

    @Override
    public void addCart(long id) {

    }

    @Override
    public void delCart(long id) {

    }

    private RowMapper<Lesson> lessonRowMapper() {
        return (rs, rowNum) -> {
            Lesson lesson = new Lesson();
            lesson.setLessonId(rs.getLong("lnum"));
            lesson.setTitle(rs.getString("title"));
            lesson.setTutor(rs.getString("tutor"));
            lesson.setPrice(rs.getInt("price"));
            lesson.setIntro(rs.getString("intro"));

            return lesson;
        };
    }


}
