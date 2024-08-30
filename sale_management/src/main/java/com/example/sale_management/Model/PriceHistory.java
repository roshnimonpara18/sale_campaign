package com.example.sale_management.Model;

import jakarta.persistence.*;


import java.time.LocalDate;


@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private long price;

    @Column(name = "discount_price")
    private float discount;

    @Column(name = "date")
    private LocalDate localDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public float getDiscountPrice() {
        return discount;
    }

    public void setDiscountPrice(float discount) {
        this.discount = discount;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }


    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
