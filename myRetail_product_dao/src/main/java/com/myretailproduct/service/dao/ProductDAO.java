package com.myretailproduct.service.dao;

import com.myretailproduct.service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by agane13 on 11/10/17.
 */

public interface ProductDAO extends MongoRepository<Product, Integer> {

    public Product findByProductIdentifier(Integer productIdentifier);

}

