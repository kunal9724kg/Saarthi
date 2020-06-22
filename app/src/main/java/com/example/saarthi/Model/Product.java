package com.example.saarthi.Model;

public class Product {
    Long id;
    String productname;
    Long price;
    Long quantity;

    public Product() {
    }

    public Product(Long id, String productname, Long price, Long quantity) {
        this.id = id;
        this.productname = productname;
        this.price=  price;
        this.quantity = quantity;
    }

    public String getProductname() {
        return productname;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }
    public Long getId() {
        return id;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}