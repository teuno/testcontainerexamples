package nl.teuno.testcontainers;

import nl.teuno.testcontainers.models.Category;
import nl.teuno.testcontainers.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
        //to use the autowired Datasource. We dynamically override 3 properties of this
class CategoryRepositoryTest extends PostgresSetup {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCategorySize() {
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories.size()).isEqualTo(13L);
        System.out.println(postgreSQLContainer.getFirstMappedPort());
    }

    @Test
    void testChildCategorySize() {
        List<Category> categories = categoryRepository.getChildCategories();
        assertThat(categories.size()).isEqualTo(8L);
        System.out.println(postgreSQLContainer.getFirstMappedPort());
    }
}
