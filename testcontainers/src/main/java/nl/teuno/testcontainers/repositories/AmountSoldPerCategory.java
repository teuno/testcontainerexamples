package nl.teuno.testcontainers.repositories;

import java.math.BigDecimal;

public interface AmountSoldPerCategory {
    String getProductname();
    BigDecimal getPrice();
    String getCategoryName();
    BigDecimal getTotalSold();
}
