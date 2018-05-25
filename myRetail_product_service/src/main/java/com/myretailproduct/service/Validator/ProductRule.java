package com.myretailproduct.service.Validator;

import com.myretailproduct.service.model.Product;

/**
 * Interface for validator
 */
public interface ProductRule<F, T> {

    public void validate(Product product);


}
