package com.myretailproduct.service.Validator;

import com.myretailproduct.service.beans.Product;

/**
 * Interface for validator
 */
public interface Validator<F,T> {

    public Boolean isValidProduct(Product product);

}
