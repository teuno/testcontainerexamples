package nl.teuno.testcontainers;

import nl.teuno.testcontainers.repositories.AmountSoldPerCategory;
import nl.teuno.testcontainers.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
        //to use the autowired Datasource. We dynamically override 3 properties of this
class ProductRepositoryTest extends PostgresSetup{

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
