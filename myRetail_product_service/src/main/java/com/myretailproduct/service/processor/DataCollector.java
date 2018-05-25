package com.myretailproduct.service.processor;


import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.beans.productMasterApi.ProductInformation;
import com.myretailproduct.service.dao.ProductDAO;
import com.myretailproduct.service.model.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class DataCollector {

    @Autowired
    private ProductDAO productDAO;
    @Value("${product.url}")
    private String productURL;
    private RestTemplate restTemplate;

    @PostConstruct
    private void after() {
        restTemplate = ConnectionUtil.getRestTemplate();
    }

    static Logger log = Logger.getLogger(DataCollector.class);

    ProductInformation getProductDetail(Integer id) throws ProductInformationNotAvailableException {

        String myProductURL = productURL.replace("{product_identifier}", String.valueOf(id));
        ProductInformation response = null;
        try {
            log.info("Calling redsky service for ProductDetailInfo:" + myProductURL);
            response = restTemplate.getForObject(myProductURL, ProductInformation.class);
        } catch (HttpClientErrorException clientException) {
            log.error(clientException.getMessage());
            if (clientException.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("ProductDetailInfo not available in redsky service: " + id);
                throw new ProductInformationNotAvailableException("ProductDetailInfo not available in service");
            }
        }
        log.info(response);
        if (response == null) {
            throw new ProductInformationNotAvailableException("ProductDetailInfo not available in service");
        }

        return response;
    }

    ProductDetailPrice getProductPrice(Integer id) throws ProductInformationNotAvailableException {
        Product product = productDAO.findByProductIdentifier(id);
        log.info("Details from repo:" + product);
        if (product == null || product.getAmount() == null || product.getAmount().getPrice() == null) {
            throw new ProductInformationNotAvailableException("ProductDetailInfo or Pricing is unavailable");
        }
        ProductDetailPrice price = new ProductDetailPrice();
        price.setCurrencyCode(product.getAmount().getCurrencyCode());
        price.setAmount(product.getAmount().getPrice());
        return price;
    }
}
