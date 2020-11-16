package com.tomi.shop.db;

import com.tomi.shop.core.Product;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO extends AbstractDAO<Product> {
    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public Product create(Product product) {
        return persist(product);
    }

    @SuppressWarnings("unchecked")
    @UnitOfWork
    public List<Product> findAll() {
        return list((Query<Product>) namedQuery("com.tomi.shop.core.Product.findAll"));
    }

    @SuppressWarnings("unchecked")
    @UnitOfWork
    public long countProducts() {
        Query<Long> q = (Query<Long>)namedQuery("com.tomi.shop.core.Product.count");
        return q.uniqueResult();
    }

}
