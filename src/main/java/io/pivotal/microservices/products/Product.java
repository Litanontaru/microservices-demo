package io.pivotal.microservices.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 11:40 AM
 */
@Entity
@Table(name = "T_PRODUCT")
public class Product {
    @Id
    protected Long id;

    protected String number;

    @Column(name = "name")
    protected String title;

    protected BigDecimal price;

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
