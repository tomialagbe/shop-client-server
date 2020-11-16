package com.tomi.shop.db;

import com.tomi.shop.core.Product;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL57Dialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(DropwizardExtensionsSupport.class)
public class ProductDAOIntegrationTest {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql");

    public DAOTestExtension database = DAOTestExtension.newBuilder()
            .customizeConfiguration(c -> c.setProperty(AvailableSettings.DIALECT, MySQL57Dialect.class.getName()))
            .setDriver(MY_SQL_CONTAINER.getDriverClassName())
            .setUrl(MY_SQL_CONTAINER.getJdbcUrl())
            .setUsername(MY_SQL_CONTAINER.getUsername())
            .setPassword(MY_SQL_CONTAINER.getPassword())
            .addEntityClass(Product.class)
            .build();

    private ProductDAO productDAO;

    @BeforeEach
    public void setUp() {
        productDAO = new ProductDAO(database.getSessionFactory());
    }

    @Test
    public void createsProduct() {
        Product p = new Product("Product A", "Product A Description", "https://product_url.com", 10, new BigDecimal("1000"));
        Product productA = database.inTransaction(() -> productDAO.create(p));

        assertThat(productA.getName()).isEqualTo("Product A");
        assertThat(productA.getDescription()).isEqualTo("Product A Description");
        assertThat(productA.getPictureUrl()).isEqualTo("https://product_url.com");
        assertThat(productA.getQtyInStock()).isEqualTo(10);
        assertThat(productA.getPrice()).isEqualTo(new BigDecimal("1000"));
        assertThat(productA.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindAll() {
        database.inTransaction(() -> {
            productDAO.create(new Product("Product A", "Product A Description", "https://product-a-url.com", 10, new BigDecimal("1000")));
            productDAO.create(new Product("Product B", "Product B Description", "https://product-b-url.com", 5, new BigDecimal("650")));
            productDAO.create(new Product("Product C", "Product C Description", "https://product-c-url.com", 13, new BigDecimal("420")));
        });

        final List<Product> productList = database.inTransaction(() -> productDAO.findAll());
        assertThat(productList).isNotEmpty();
        assertThat(productList.size()).isEqualTo(3);
        assertThat(productList).extracting("name").containsOnly("Product A", "Product B", "Product C");
        assertThat(productList).extracting("description").containsOnly("Product A Description", "Product B Description", "Product C Description");
        assertThat(productList).extracting("qtyInStock").containsOnly(10, 5, 13);
    }

    @Test
    public void testCountAll() {
        database.inTransaction(() -> {
            productDAO.create(new Product("Product A", "Product A Description", "https://product-a-url.com", 10, new BigDecimal("1000")));
            productDAO.create(new Product("Product B", "Product B Description", "https://product-b-url.com", 5, new BigDecimal("650")));
            productDAO.create(new Product("Product C", "Product C Description", "https://product-c-url.com", 13, new BigDecimal("420")));
        });

        final long numProducts = productDAO.countProducts();
        assertThat(numProducts).isEqualTo(3);
    }

}
