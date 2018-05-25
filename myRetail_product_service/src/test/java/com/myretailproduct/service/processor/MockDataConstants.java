package com.myretailproduct.service.processor;

import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;

public class MockDataConstants {

    public static final Integer sonyIdentifier = new Integer(13860428);
    public static final String sonyTitle = "Sony Bravia TV";
    public static final ProductDetailPrice sonyPrice = new ProductDetailPrice();

    public static final ProductDetail sonyProduct;

    public static final String productUrl = "http://redsky.target.com/v2/pdp/tcin/{product_identifier}?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";



    static {
        sonyProduct = new ProductDetail();
        sonyProduct.setIdentifier(sonyIdentifier);
        sonyProduct.setTitle(sonyTitle);
        sonyPrice.setAmount(15.88d);
        sonyPrice.setCurrencyCode("USD");
        sonyProduct.setPrice(sonyPrice);
    }
}
