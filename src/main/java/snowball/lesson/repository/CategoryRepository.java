package snowball.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowball.lesson.entity.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
