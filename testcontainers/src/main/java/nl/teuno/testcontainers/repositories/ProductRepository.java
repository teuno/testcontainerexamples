package nl.teuno.testcontainers.repositories;

import nl.teuno.testcontainers.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(nativeQuery = true, value = "SELECT\n" +
            "   p.name productname,\n" +
            "   price,\n" +
            "   c.name category_name,\n" +
            "   SUM (price) OVER (\n" +
            "      PARTITION BY c.name\n" +
            "   ) total_sold\n" +
            "FROM\n" +
            "   tbl_product p\n" +
            "   INNER JOIN\n" +
            "      tbl_category c USING (category_id)\n" +
            "ORDER BY category_name")
    List<AmountSoldPerCategory> getAmountSoldPerCategory();
}
