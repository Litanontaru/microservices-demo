package io.pivotal.microservices.services.web;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;

/**
 * @author Andrei_Yakushin
 * @since 8/17/2016 5:41 PM
 */
@JsonRootName("Product")
public class Product {
    protected Long id;
    protected String number;
    protected String title;
    protected BigDecimal price;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
