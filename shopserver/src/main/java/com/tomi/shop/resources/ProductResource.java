package com.tomi.shop.resources;

import com.tomi.shop.core.Product;
import com.tomi.shop.db.ProductDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    private final ProductDAO productDAO;

    public ProductResource(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GET
    @UnitOfWork
    public List<Product> getAllProducts(@Context HttpServletResponse response) {
        return productDAO.findAll();
    }
}
