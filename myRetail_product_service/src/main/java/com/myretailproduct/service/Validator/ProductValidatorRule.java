package com.myretailproduct.service.Validator;

import com.myretailproduct.service.Exception.InvalidProductException;
import com.myretailproduct.service.model.Product;

public class ProductValidatorRule implements ProductRule {

    @Override
    public void validate(Product product) {
        if (product == null || product.getProductIdentifier() == null) {
            throw new InvalidProductException("Invalid ProductDetailInfo details");
        }
    }
}
