package com.tomi.shop;

import com.tomi.shop.core.Product;
import com.tomi.shop.db.DBSeeder;
import com.tomi.shop.db.ProductDAO;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(DropwizardExtensionsSupport.class)
public class DockerIntegrationTest {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql");
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-docker-shop-server.yml");

    public static final DropwizardAppExtension<ShopAppConfiguration> APP = new DropwizardAppExtension<>(
            ShopApp.class, CONFIG_PATH,
            ConfigOverride.config("database.url", MY_SQL_CONTAINER::getJdbcUrl),
            ConfigOverride.config("database.user", MY_SQL_CONTAINER::getUsername),
            ConfigOverride.config("database.password", MY_SQL_CONTAINER::getPassword)
    );

    @BeforeAll
    public static void migrateDb() throws Exception {
        APP.getApplication().run("db", "migrate", CONFIG_PATH);
        APP.getApplication().run("dbseed", CONFIG_PATH);
    }

    @Test
    public void testGetProducts() {

        List<Product> products = APP.client().target("http://localhost:" + APP.getLocalPort() + "/products")
                .request()
                .get(new GenericType<List<Product>>(){});
        System.out.println(products.size());
    }
}
