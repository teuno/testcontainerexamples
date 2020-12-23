package nl.teuno.testcontainers.repositories;

import nl.teuno.testcontainers.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(nativeQuery = true, value = "WITH RECURSIVE category_tree AS (\n" +
            "    SELECT *\n" +
            "    FROM tbl_category\n" +
            "    WHERE category_id = 4\n" +
            "    UNION ALL\n" +
            "    SELECT c.*\n" +
            "    FROM tbl_category c\n" +
            "             INNER JOIN category_tree ON category_tree.category_id = c.parent_category_id\n" +
            ")\n" +
            "\n" +
            "SELECT *\n" +
            "FROM category_tree\n" +
            "WHERE category_id != 4\n" +
            "ORDER BY parent_category_id")
    List<Category> getChildCategories();
}

