package com.myretailproduct.service.beans.productMasterApi;

/**
 * Created by agane13 on 11/12/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailResponse {

    @JsonProperty("product")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductTitle() throws ProductInformationNotAvailableException {
        try {
            return getProduct().getItem().getProductDescription().getTitle();
        } catch (Exception e) {
            throw new ProductInformationNotAvailableException("Item Online Description is unavailable");
        }
    }

    @Override
    public String toString() {
        return "Response [productCompositeResponse=" + product + "]";
    }
}

