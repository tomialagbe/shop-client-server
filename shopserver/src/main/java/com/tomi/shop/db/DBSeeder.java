package com.tomi.shop.db;

import com.tomi.shop.core.Product;
import io.dropwizard.hibernate.UnitOfWork;

import java.math.BigDecimal;

public class DBSeeder {
    private final ProductDAO productDAO;

    public DBSeeder(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @UnitOfWork
    public void createProducts() {
        long numProducts = productDAO.countProducts();
        if (numProducts == 0) {
            productDAO.create(new Product("Samsung Galaxy A20", "lorem ipsup dolor sit amet lorem ipsum dolor sit", "https://i.imgur.com/KFojDGa.jpg", 15, new BigDecimal("450")));
            productDAO.create(new Product("iPhone 12", "lorem ipsup dolor sit amet lorem ipsum dolor sit", "https://i.imgur.com/KFojDGa.jpg", 9, new BigDecimal("950")));
            productDAO.create(new Product("iPhone 11 Pro", "lorem ipsup dolor sit amet lorem ipsum dolor sit", "https://i.imgur.com/KFojDGa.jpg", 2, new BigDecimal("1100")));
            productDAO.create(new Product("Samsung Galaxy S20", "lorem ipsup dolor sit amet lorem ipsum dolor sit", "https://i.imgur.com/KFojDGa.jpg", 11, new BigDecimal("900")));
        }
    }
}
