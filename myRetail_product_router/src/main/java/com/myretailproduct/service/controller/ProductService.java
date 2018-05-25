package com.myretailproduct.service.controller;

import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.processor.ProductProcessor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/retail")
/**
 * API suite for product service
 */
public class ProductService {


    static Logger log = Logger.getLogger(ProductService.class);
    @Autowired
    ProductProcessor productProcessor;

    /**
     * Provides the product details
     *
     * @param identifier
     * @param request
     * @return
     * @throws ProductInformationNotAvailableException
     */
    @RequestMapping(value = "v1/products/{identifier}", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProductDetail> getProduct(@PathVariable("identifier") Integer identifier,
                                                    HttpServletRequest request) throws ProductInformationNotAvailableException {
        HttpHeaders headers = new HttpHeaders();
        log.info("ProductService.getProductInfo(" + identifier + ")");
        ProductDetail productDetail = productProcessor.getProductDetails(identifier);

        return new ResponseEntity<ProductDetail>(productDetail, headers, HttpStatus.OK);
    }


    /**
     * Stores product info
     * Accepts a json request body in the following format
     *
     * @param identifier
     * @param newPrice
     * @return
     * @throws ProductInformationNotAvailableException
     */
    @RequestMapping(value = "v1/products/{identifier}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> updateProduct(@PathVariable("identifier") Integer identifier, @RequestBody ProductDetailPrice newPrice)
            throws ProductInformationNotAvailableException {
        log.info("ProductService.updateProduct(" + identifier + "); newPrice = " + newPrice);
        HttpHeaders headers = new HttpHeaders();
        productProcessor.updateProductPrice(identifier, newPrice);

        return new ResponseEntity<String>(null, headers, HttpStatus.OK);
    }

}
