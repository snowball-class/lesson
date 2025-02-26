package snowball.lesson.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import snowball.lesson.dto.GetLessonDetailsDto;
import snowball.lesson.dto.GetLessonDto;
import snowball.lesson.dto.GetMemberLessonDto;
import snowball.lesson.exception.LessonNotFoundException;
import snowball.lesson.lesson.Lesson;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcLessonRepository implements LessonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLessonRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // comment : 추후 리뷰테이블 추가되면 정렬 추가할 것
    @Override
    public List<GetLessonDto> getEventLessonList(long eventId) {
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
                    + "or (select category_name from category c WHERE l.category_id = c.category_id) LIKE '%"+keyword+"%'"
                    + ")";

        return jdbcTemplate.query(sql, lessonListMapper());
    }

    @Override
    public GetLessonDetailsDto findById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT l.*, c.category_name FROM lesson AS l LEFT JOIN category AS c ON l.category_id = c.category_id where lesson_id = ? and deleted = 0"
                    , lessonDetailMapper()
                    , id);
        } catch (EmptyResultDataAccessException e) {
            throw new LessonNotFoundException("Lesson not found with ID: " + id);
        }
    }

    @Override
    public List<GetMemberLessonDto> getMemberLessonList(UUID memberId){
        String sql = "SELECT * FROM lesson_student A JOIN lesson B ON A.lesson_id = B.lesson_id WHERE A.student_id = UUID_TO_BIN('" + memberId.toString() + "');";
        return jdbcTemplate.query(sql, memberLessonListMapper());
    }

    // bulk update query
    @Override
    public void bulkUpdateLessonsForEvent(long eventId,
                                          Integer discountRate,
                                          LocalDateTime discountStartDate, LocalDateTime discountFinishDate,
                                          List<Long> lessonIds) {
        String sql = "UPDATE lesson SET event_id = :eventId, discount_rate = :discountRate, " +
                "discount_sdate = :discountStartDate, discount_fdate = :discountFinishDate " +
                "WHERE lesson_id IN (:lessonIds)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("eventId", eventId);
        parameters.addValue("discountRate", discountRate);
        parameters.addValue("discountStartDate", Timestamp.valueOf(discountStartDate));
        parameters.addValue("discountFinishDate", Timestamp.valueOf(discountFinishDate));
        parameters.addValue("lessonIds", lessonIds);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedParameterJdbcTemplate.update(sql, parameters);
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
            lesson.setEventId(rs.getLong("event_id"));
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
            lesson.setEventId(rs.getLong("event_id"));

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
            lesson.setEventId(rs.getLong("event_id"));

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



    private RowMapper<GetMemberLessonDto> memberLessonListMapper() {
        return (rs, rowNum) -> {
            GetMemberLessonDto lesson = new GetMemberLessonDto();
            lesson.setLessonId(rs.getLong("lesson_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setTitle(rs.getString("tutor"));
            lesson.setCategoryId(rs.getInt("category_id"));
            lesson.setIsEnrolled(rs.getInt("is_enrolled"));

            int thumbId = rs.getInt("thumbnail_id");
            lesson.setThumbnail(getThumbUrl(thumbId));  // 이미지 URL

            Timestamp sdate = rs.getTimestamp("start_date");
            lesson.setStartDate(sdate == null ? null : sdate.toLocalDateTime());

            return lesson;
        };
    }

    public int calDiscount(int price, int discountPercent) {
        return price * discountPercent / 100;
    }
    public String getThumbUrl(int thumbId){
        return "";
    }
    public float calRating(){
        return 3.5F;
    };
}
