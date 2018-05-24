package com.myretailproduct.service.beans;

import com.myretailproduct.service.Exception.InvalidProductException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Product bean contains the product details
 */
@Document
public class Product {
    @Id
    Integer productIdentifier;
    String title;
    Amount amount;
    LocalDateTime creationDateTime;
    LocalDateTime lastUpdatedTime;

    public Product(Integer productIdentifier, Amount amount) throws InvalidProductException {

        this.productIdentifier = productIdentifier;
        this.amount = amount;
    }

    public Integer getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(Integer productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productIdentifier=" + productIdentifier +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", creationDateTime=" + creationDateTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}
