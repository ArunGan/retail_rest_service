package com.myretailproduct.service.processor;

import com.myretail.product.model.ProductDetail;
 import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.InvalidProductException;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.Validator.Validator;
import com.myretailproduct.service.Validator.ValidatorUtil;
import com.myretailproduct.service.beans.Amount;
 import com.myretailproduct.service.beans.Product;
 import com.myretailproduct.service.beans.productMasterApi.ProductDetailResponse;
 import com.myretailproduct.service.dao.ProductDAO;
 import org.apache.log4j.Logger;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.http.HttpStatus;
 import org.springframework.stereotype.Service;
 import org.springframework.web.client.HttpClientErrorException;
 import org.springframework.web.client.RestTemplate;

 import javax.annotation.PostConstruct;

/**
 * Process the product info, contains methods to retrieve or update product info
 */
@Service
public class ProductProcessorImpl implements ProductProcessor{

    static Logger log = Logger.getLogger(ProductProcessorImpl.class);
    @Autowired
    private ProductDAO productDAO;
    @Value("${product.url}")
    private String productURL;
    private RestTemplate restTemplate;

    @PostConstruct
    private void after() {
        restTemplate = ConnectionUtil.getRestTemplate();
    }

    /**
     * get the product details from data base
     * @param id
     * @return
     * @throws ProductInformationNotAvailableException
     */
    public ProductDetail getProductDetails(Integer id) throws ProductInformationNotAvailableException {

        ProductDetailResponse response = getProductName(id);
        ProductDetail product = buildProduct(id, response);

        return product;
    }

    /**
     * updates the product's price in DB
     * @param id
     * @param price
     * @return
     * @throws ProductInformationNotAvailableException
     */
    public void updateProductPrice(Integer id, ProductDetailPrice price) throws ProductInformationNotAvailableException {

        Product product = new Product(id, new Amount(price.getAmount(), price.getCurrencyCode()));

        ValidatorUtil.validateProduct(product);

        Product savedProduct = productDAO.save(product);

        if(savedProduct ==null){
            throw new ProductInformationNotAvailableException("Product not saved");
        }

    }

    private ProductDetailResponse getProductName(Integer id) throws ProductInformationNotAvailableException {

        String myProductURL = productURL.replace("{product_identifier}", String.valueOf(id));
        ProductDetailResponse response = null;
        try {
            log.info("Calling redsky service for Product:"+myProductURL);
            response = restTemplate.getForObject(myProductURL, ProductDetailResponse.class);
        } catch (HttpClientErrorException clientException) {
            log.error(clientException.getMessage());
            if (clientException.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Product not available in redsky service: " + id);
                throw new ProductInformationNotAvailableException("Product not available in service");
            }
        }
        log.info(response);
        if (response == null) {
            throw new ProductInformationNotAvailableException("Product not available in service");
        }

        return response;
    }

    private ProductDetail buildProduct(Integer id, ProductDetailResponse productResponse) throws ProductInformationNotAvailableException {
        ProductDetail product = new ProductDetail();
        product.setIdentifier(id);
        product.setTitle(productResponse.getProductTitle());
        product.setPrice(retrievePrice(id));

        return product;
    }

    private ProductDetailPrice retrievePrice(Integer id) throws ProductInformationNotAvailableException {
        Product product = productDAO.findByProductIdentifier(id);
        log.info("Details from repo:" + product);
        if (product == null || product.getAmount() == null || product.getAmount().getPrice() == null) {
            throw new ProductInformationNotAvailableException("Product or Pricing is unavailable");
        }
        ProductDetailPrice price = new ProductDetailPrice();
        price.setCurrencyCode(product.getAmount().getCurrencyCode());
        price.setAmount(product.getAmount().getPrice());
        return price;
    }
}
