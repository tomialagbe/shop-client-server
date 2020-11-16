package com.tomi.shop.resources;

import com.tomi.shop.core.Product;
import com.tomi.shop.db.ProductDAO;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.core.GenericType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(DropwizardExtensionsSupport.class)
public class ProductResourceTest {
    private static final ProductDAO productDAO = mock(ProductDAO.class);
    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new ProductResource(productDAO))
            .build();

    private ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("Product A", "Product A Description", "https://product_url.com", 10, new BigDecimal("1000"));
    }

    @AfterEach
    public void tearDown() {
        reset(productDAO);
    }


    @Test
    public void listProducts() throws Exception {
        final List<Product> people = Collections.singletonList(product);
        when(productDAO.findAll()).thenReturn(people);

        final List<Product> response = RESOURCES.target("/products")
                .request().get(new GenericType<List<Product>>() {});

        verify(productDAO).findAll();
        assertThat(response).containsAll(people);
    }
}
