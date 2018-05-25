package com.myretailproduct.service.model;

import com.myretailproduct.service.Exception.InvalidProductException;

/**
 * Created by agane13 on 11/11/17.
 */
public class Amount {

    public Double price;
    public String currencyCode;

    public Amount() {

    }

    public Amount(Double price, String currencyCode) throws InvalidProductException {

        setPrice(price);
        setCurrencyCode(currencyCode);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {

        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
