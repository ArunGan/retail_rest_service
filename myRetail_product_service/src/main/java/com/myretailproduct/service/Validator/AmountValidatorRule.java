package com.myretailproduct.service.Validator;

import com.myretailproduct.service.Exception.InvalidProductException;
import com.myretailproduct.service.model.Product;
import org.springframework.util.StringUtils;

import java.util.Currency;

public class AmountValidatorRule implements ProductRule {

    @Override
    public void validate(Product product) {
        if (product == null || product.getAmount() == null ||
                product.getAmount().getPrice() == null || product.getAmount().getPrice() <= 0
                || StringUtils.isEmpty(product.getAmount().getCurrencyCode())
                || !isValidCurrency(product.getAmount().getCurrencyCode())) {
            throw new InvalidProductException("Invalid Product amount");
        }

    }


    public boolean isValidCurrency(String currency) {
        boolean valid = true;
        try {
            Currency.getInstance(currency);

        } catch (IllegalArgumentException e) {
            valid = false;
        }
        return valid;
    }
}
