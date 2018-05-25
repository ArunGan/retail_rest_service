package com.myretailproduct.service.beans.productMasterApi;

/**
 * Created by agane13 on 05/17/18.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInformation {

    @JsonProperty("product")
    private ProductDetailInfo productInfo;

    public ProductDetailInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductDetailInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductTitle() throws ProductInformationNotAvailableException {
        try {
            return getProductInfo().getItemInfo().getProductDescription().getTitle();
        } catch (Exception e) {
            throw new ProductInformationNotAvailableException("ItemInfo Online Description is unavailable");
        }
    }

    @Override
    public String toString() {
        return "Response [productInfo=" + productInfo + "]";
    }
}

