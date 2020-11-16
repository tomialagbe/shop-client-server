package com.tomi.shop;

import com.tomi.shop.core.Product;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.GenericType;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
public class IntegrationTest {

    private static final String TMP_FILE = createTempDbFile();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-shop-server.yml");

    public static final DropwizardAppExtension<ShopAppConfiguration> RULE = new DropwizardAppExtension<>(
            ShopApp.class, CONFIG_PATH,
            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

    @BeforeAll
    public static void migrateDb() throws Exception {
        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }


    private static String createTempDbFile() {
        try {
            return File.createTempFile("test-shop-server", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void testFindAllProducts() throws Exception {
        final List<Product> products = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/products")
                .request()
                .get(new GenericType<List<Product>>(){});

        assertThat(products).isNotEmpty();
    }
}
