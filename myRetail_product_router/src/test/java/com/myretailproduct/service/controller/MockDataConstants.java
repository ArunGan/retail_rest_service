package com.myretailproduct.service.controller;

import com.myretail.product.model.ProductDetail;

public class MockDataConstants {

    public static Integer sonyIdentifier = new Integer(13860428);
    public static String sonyTitle = "Sony Bravia TV";

    public static ProductDetail sonyProduct = new ProductDetail();


    static {
        sonyProduct.setIdentifier(sonyIdentifier);
        sonyProduct.setTitle(sonyTitle);
    }
}
