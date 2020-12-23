package nl.teuno.testcontainers;

import nl.teuno.testcontainers.models.Category;
import nl.teuno.testcontainers.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:13:///mytestcontainers"
})//https://www.testcontainers.org/modules/databases/jdbc/
@Sql("/init.sql")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCategorySize() {
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories.size()).isEqualTo(13L);
    }

    @Test
    @Sql("/init.sql")
    void testChildCategorySize() {
        List<Category> categories = categoryRepository.getChildCategories();
        assertThat(categories.size()).isEqualTo(8L);
    }
}
