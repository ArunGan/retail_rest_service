package com.myretailproduct.service.beans;

import com.myretailproduct.service.Exception.InvalidProductException;
import org.springframework.util.StringUtils;

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

        if (price == null || price <= 0) {
            throw new InvalidProductException("Amount", "Amount", "Invalid price, should be greated than zero");
        }
        this.price = price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {

        if (StringUtils.isEmpty(currencyCode)) {
            throw new InvalidProductException("Amount", "Amount", "Currency code is Invalid");
        }
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
