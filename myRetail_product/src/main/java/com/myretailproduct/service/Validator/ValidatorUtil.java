package com.myretailproduct.service.Validator;

import com.myretailproduct.service.Exception.InvalidProductException;
import com.myretailproduct.service.beans.Product;

/**
 * Product validator utilities
 */
public class ValidatorUtil {

    public static void validateProduct(Product product) throws InvalidProductException{

        Validator validator = validateproduct -> product != null && product.getProductIdentifier()!= null && product.getAmount() !=null;

        if(!validator.isValidProduct(product)){
            throw new InvalidProductException("Product identifier or price is missing");
        }

    }
}
