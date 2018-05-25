package com.myretailproduct.service.processor;

import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.Validator.ValidatorUtil;
import com.myretailproduct.service.beans.productMasterApi.ProductInformation;
import com.myretailproduct.service.dao.ProductDAO;
import com.myretailproduct.service.model.Amount;
import com.myretailproduct.service.model.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Process the product info, contains methods to retrieve or update product info
 */
@Service
public class ProductProcessorImpl implements ProductProcessor {

    static Logger log = Logger.getLogger(ProductProcessorImpl.class);

    @Autowired
    DataCollector dataCollector;

    @Autowired
    private ProductDAO productDAO;



    /**
     * get the product details from data base
     *
     * @param id
     * @return
     * @throws ProductInformationNotAvailableException
     */
    public ProductDetail getProductDetails(Integer id) throws ProductInformationNotAvailableException {

        //TODO: Make parallel calls
        ProductInformation productInformation = dataCollector.getProductDetail(id);
        ProductDetailPrice detailPrice = dataCollector.getProductPrice(id);

        Map<String,Object> productsInfo = new HashMap<>();

        productsInfo.put("productInformation",productInformation);
        productsInfo.put("detailPrice",detailPrice);

        ProductDetail product = buildProduct(id, productsInfo);

        return product;
    }

    /**
     * updates the product's price in DB
     *
     * @param id
     * @param price
     * @return
     * @throws ProductInformationNotAvailableException
     */
    public Product updateProductPrice(Integer id, ProductDetailPrice price) throws ProductInformationNotAvailableException {

        Product product = new Product(id, new Amount(price.getAmount(), price.getCurrencyCode()));

        ValidatorUtil.validateProduct(product);

        Product savedProduct = productDAO.save(product);

        if (savedProduct == null) {
            throw new ProductInformationNotAvailableException("ProductDetailInfo not saved");
        }

        return savedProduct;
    }



    private ProductDetail buildProduct(Integer id, Map productInfo) throws ProductInformationNotAvailableException {

       ProductInformation productInformation =  (ProductInformation) productInfo.get("productInformation");
        ProductDetailPrice detailPrice = (ProductDetailPrice) productInfo.get("detailPrice");


        ProductDetail product = new ProductDetail();
        product.setIdentifier(id);
        product.setTitle(productInformation.getProductTitle());
        product.setPrice(detailPrice);

        return product;
    }


}
