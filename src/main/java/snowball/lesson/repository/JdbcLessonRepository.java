package snowball.lesson.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.lesson.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLessonRepository implements LessonRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLessonRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // comment : 추후 리뷰테이블 추가되면 정렬 추가할 것
    @Override
    public List<GetLessonDto> getEventLessonList(int eventId) {
        return jdbcTemplate.query(
                "select * from lesson where event_id = ? and deleted = 0"
                    , lessonListMapper()
                    , eventId);
    }

    // comment : 추후 리뷰테이블 추가되면 정렬 추가할 것
    @Override
    public List<GetLessonDto> getLessonList(int categoryId) {
        return jdbcTemplate.query(
                "select * from lesson where category_id = ? and deleted = 0"
                , lessonListMapper()
                , categoryId);
    }

    // comment : 추후 리뷰테이블 추가되면 정렬 추가할 것
    @Override
    public List<GetLessonDto> getSearchLesson(String keyword){
        String sql  = "select * from lesson l "
                    + "where deleted = 0 "
                    + "and (title like '%"+keyword+"%'"
                    + "or tutor like '%"+keyword+"%'"
                    + "or content like '%"+keyword+"%'"
                    + "or content2 like '%"+keyword+"%'"
                    + "or (select category_name from category c WHERE l.category_id = c.category_id) LIKE '%"+keyword+"%'"
                    + ")";

        return jdbcTemplate.query(sql, lessonListMapper());
    }

    @Override
    public GetLessonDetailsDto findById(long id) {
            var T = jdbcTemplate.queryForObject(
                    "SELECT l.*, c.category_name FROM lesson AS l LEFT JOIN category AS c ON l.category_id = c.category_id where lesson_id = ? and deleted = 0"
                    , lessonDetailMapper()
                    , id);
        //return Optional.ofNullable(T).map(GetLessonDetailsDto).orElseThrow(LessonNotFoundException("Lesson not found with ID: " + id));
        return Optional.ofNullable(T)
                .map(item -> new GetLessonDetailsDto()) // 'item'을 사용하여 GetLessonDetailsDto 생성
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with ID: " + id)); // 람다식으로 예외 생성

    }


    private RowMapper<Lesson> lessonRowMapper() {
        return (rs, rowNum) -> {
            Lesson lesson = new Lesson();

            lesson.setLessonId(rs.getLong("lesson_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setCategoryId(rs.getInt("category_id"));
            lesson.setTutor(rs.getString("tutor"));
            lesson.setPrice(rs.getInt("price"));
            lesson.setContent1(rs.getString("content"));
            lesson.setContent2(rs.getString("content2"));
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

    private RowMapper<GetLessonDetailsDto> lessonDetailMapper() {
        return (rs, rowNum) -> {
            GetLessonDetailsDto lesson = new GetLessonDetailsDto();
            lesson.setLessonId(rs.getLong("lesson_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setTutor(rs.getString("tutor"));
            lesson.setCategoryId(rs.getInt("category_id"));
            lesson.setCategoryName(rs.getString("category_name"));
            lesson.setContent1(rs.getString("content"));
            lesson.setContent2(rs.getString("content2"));
            lesson.setEventId(rs.getInt("event_id"));

            int thumbId = rs.getInt("thumbnail_id");
            lesson.setThumbnail(getThumbUrl(thumbId));  // 이미지 URL

            int price = rs.getInt("price");
            int discountRate = rs.getInt("discount_rate");
            int discountPrice = calDiscount(price, discountRate);
            lesson.setNetPrice(price);
            lesson.setDiscountRate(discountRate);
            lesson.setSalePrice(discountPrice);

            Timestamp sdate = rs.getTimestamp("discount_sdate");
            Timestamp fdate = rs.getTimestamp("discount_fdate");
            lesson.setDiscountStartDate(sdate == null ? null : sdate.toLocalDateTime());
            lesson.setDiscountFinishDate(fdate == null ? null : fdate.toLocalDateTime());

            return lesson;
        };
    }

    private RowMapper<GetLessonDto> lessonListMapper() {
        return (rs, rowNum) -> {
            GetLessonDto lesson = new GetLessonDto();
            lesson.setLessonId(rs.getLong("lesson_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setCategoryId(rs.getInt("category_id"));
            lesson.setEventId(rs.getInt("event_id"));

            int thumbId = rs.getInt("thumbnail_id");
            lesson.setThumbnail(getThumbUrl(thumbId));  // 이미지 URL

            int price = rs.getInt("price");
            int discountRate = rs.getInt("discount_rate");
            int discountPrice = calDiscount(price, discountRate);
            lesson.setNetPrice(price);
            lesson.setDiscountRate(discountRate);
            lesson.setSalePrice(discountPrice);

            Timestamp sdate = rs.getTimestamp("discount_sdate");
            Timestamp fdate = rs.getTimestamp("discount_fdate");
            lesson.setDiscountStartDate(sdate == null ? null : sdate.toLocalDateTime());
            lesson.setDiscountFinishDate(fdate == null ? null : fdate.toLocalDateTime());

            return lesson;
        };
    }


    public int calDiscount(int price, int discountPercent) {
        return price * discountPercent / 100;
    }
    public String getThumbUrl(int thumbId){
        return "";
    }
}
