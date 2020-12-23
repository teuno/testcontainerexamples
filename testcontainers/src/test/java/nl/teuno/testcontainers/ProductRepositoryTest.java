package nl.teuno.testcontainers;

import nl.teuno.testcontainers.repositories.AmountSoldPerCategory;
import nl.teuno.testcontainers.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
        //to use the autowired Datasource. We dynamically override 3 properties of this
class ProductRepositoryTest {

    @Container
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>(
            "postgres:13-alpine")
            .withPassword("postgres")
            .withUsername("postgres")
            .withInitScript("init.sql");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testMaxProducts() {
        long total = productRepository.count();
        assertThat(total).isEqualTo(400L);
        System.out.println(postgreSQLContainer.getFirstMappedPort());
    }

    @Test
    void testProductsSoldPerCategory() {
        List<AmountSoldPerCategory> productsSoldOverview = productRepository.getAmountSoldPerCategory();
        assertThat(productsSoldOverview.size()).isEqualTo(400L);
        System.out.println(postgreSQLContainer.getFirstMappedPort());
    }
}
