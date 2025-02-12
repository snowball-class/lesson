package snowball.lesson.repository;

import snowball.lesson.lesson.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcLessonRepository implements LessonRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLessonRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Lesson> getEventLessonList(int eventId) {
        List<Lesson> result = jdbcTemplate.query("select * from lesson where event_id = ? and deleted = 0", lessonRowMapper(), eventId);
        return result;
    }

    @Override
    public List<Lesson> getLessonList(int categoryId) {
        List<Lesson> result = jdbcTemplate.query("select * from lesson where category_id = ? and deleted = 0", lessonRowMapper(), categoryId);
        return result;
    }

    @Override
    public Lesson findById(long id) {
        Lesson result = jdbcTemplate.queryForObject("select * from lesson where lesson_id = ? and deleted = 0", lessonRowMapper(), id);
        return result;
    }

    private RowMapper<Lesson> lessonRowMapper() {
        return (rs, rowNum) -> {
            Lesson lesson = new Lesson();
            lesson.setLessonId(rs.getLong("lesson_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setCategoryId(rs.getInt("category_id"));
            lesson.setTutor(rs.getString("tutor"));
            lesson.setPrice(rs.getInt("price"));
            lesson.setIntro(rs.getString("intro"));
            lesson.setThumbnail(rs.getInt("thumbnail_id"));
            lesson.setEventId(rs.getInt("event_id"));
            lesson.setDiscountRate(rs.getInt("discount_rate"));
            Timestamp sdate = rs.getTimestamp("discount_sdate");
            Timestamp fdate = rs.getTimestamp("discount_fdate");
            lesson.setDiscountStartDate(sdate == null ? null : sdate.toLocalDateTime());
            lesson.setDiscountFinishDate(fdate == null ? null : fdate.toLocalDateTime());
            return lesson;
        };
    }


}
