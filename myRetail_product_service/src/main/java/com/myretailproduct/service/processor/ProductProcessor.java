package com.myretailproduct.service.processor;

import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;

/**
 * Created by agane13 on 11/13/17.
 */
public interface ProductProcessor {

    public void updateProductPrice(Integer id, ProductDetailPrice price) throws ProductInformationNotAvailableException;

    public ProductDetail getProductDetails(Integer id) throws ProductInformationNotAvailableException;
}
