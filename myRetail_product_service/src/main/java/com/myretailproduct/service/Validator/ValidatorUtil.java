package com.myretailproduct.service.Validator;

import com.myretailproduct.service.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ValidatorUtil {

    public static void validateProduct(Product product) {
        List<ProductRule> rules = new ArrayList<>();

        rules.add(new ProductValidatorRule());
        rules.add(new AmountValidatorRule());

        for (ProductRule rule : rules) {
            rule.validate(product);
        }

    }
}
