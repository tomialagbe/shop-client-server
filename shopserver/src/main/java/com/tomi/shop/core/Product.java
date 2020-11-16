package com.tomi.shop.core;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
@NamedQueries(
        {
                @NamedQuery(
                        name = "com.tomi.shop.core.Product.findAll",
                        query = "SELECT p FROM Product p"
                ),
                @NamedQuery(
                        name = "com.tomi.shop.core.Product.count",
                        query = "SELECT count(p) FROM Product p"
                )
        })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "qty_in_stock", nullable = false)
    private int qtyInStock;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    public Product() {
    }

    public Product(String name, String description, String pictureUrl, int qtyInStock, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.qtyInStock = qtyInStock;
    }

    public Product(long id, String name, String description, String pictureUrl, int qtyInStock, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.qtyInStock = qtyInStock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, pictureUrl, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Product)) {
            return false;
        }

        Product product = (Product) obj;

        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(pictureUrl, product.pictureUrl) &&
                Objects.equals(price, product.price);
    }
}
