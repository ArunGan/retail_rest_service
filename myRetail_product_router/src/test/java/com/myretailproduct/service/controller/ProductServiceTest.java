package com.myretailproduct.service.controller;


import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.model.Amount;
import com.myretailproduct.service.model.Product;
import com.myretailproduct.service.processor.ProductProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@PrepareForTest({ProductService.class})
@RunWith(PowerMockRunner.class)
public class ProductServiceTest {


    @Spy
    ProductProcessor productProcessorMock = PowerMockito.mock(ProductProcessor.class);

    @InjectMocks
    ProductService restService = null;

    ProductDetail mockProd;
    Product product;
    ProductDetailPrice mockPrice;
    Map<String, String> headers;
    HttpServletRequest httpServletRequestMock;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setMockTestData();
        setMockMethodActions();
    }

    @Test
    public void testGetProduct_httpStatusSuccess() throws ProductInformationNotAvailableException {

        PowerMockito.when(productProcessorMock.getProductDetails(MockDataConstants.sonyIdentifier)).thenReturn(mockProd);
        ResponseEntity<ProductDetail> response = restService.getProduct(MockDataConstants.sonyProduct.getIdentifier(), httpServletRequestMock);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetProduct_ValidateProductDetail() throws ProductInformationNotAvailableException {

        PowerMockito.when(productProcessorMock.getProductDetails(MockDataConstants.sonyIdentifier)).thenReturn(mockProd);
        ResponseEntity<ProductDetail> response = restService.getProduct(MockDataConstants.sonyProduct.getIdentifier(), httpServletRequestMock);


        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody() instanceof ProductDetail);

        ProductDetail actualProd = response.getBody();
        Assert.assertNotNull(actualProd.getIdentifier());
        Assert.assertEquals(MockDataConstants.sonyProduct.getIdentifier(), actualProd.getIdentifier());
        Assert.assertEquals(MockDataConstants.sonyProduct.getTitle(), actualProd.getTitle());

        HttpHeaders actualHeaders = response.getHeaders();

        Assert.assertEquals("application/json", headers.get("Content-Type"));
    }

    @Test
    public void testGetProduct_responseHeaders() throws ProductInformationNotAvailableException {

        PowerMockito.when(productProcessorMock.getProductDetails(MockDataConstants.sonyIdentifier)).thenReturn(mockProd);
        ResponseEntity<ProductDetail> response = restService.getProduct(MockDataConstants.sonyProduct.getIdentifier(), httpServletRequestMock);


        Assert.assertNotNull(response);
        HttpHeaders actualHeaders = response.getHeaders();
        Assert.assertEquals("application/json", headers.get("Content-Type"));
    }

    @Test(expected = ProductInformationNotAvailableException.class)
    public void testGetProduct_unavailable() throws ProductInformationNotAvailableException {

        PowerMockito.when(productProcessorMock.getProductDetails(MockDataConstants.sonyIdentifier)).thenThrow(new ProductInformationNotAvailableException(MockDataConstants.sonyProduct.getTitle()));

        restService.getProduct(MockDataConstants.sonyProduct.getIdentifier(), httpServletRequestMock);


    }

    @Test
    public void testUpdateProduct_successUpdate() throws ProductInformationNotAvailableException {

        PowerMockito.when(productProcessorMock.updateProductPrice(MockDataConstants.sonyIdentifier, mockPrice)).thenReturn(product);

        ResponseEntity<String>  response = restService.updateProduct(MockDataConstants.sonyIdentifier,mockPrice);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test(expected = ProductInformationNotAvailableException.class)
    public void testUpdateProduct_ProdNotAvailable() throws ProductInformationNotAvailableException {

        PowerMockito.when(productProcessorMock.updateProductPrice(MockDataConstants.sonyIdentifier,mockPrice)).thenThrow(new ProductInformationNotAvailableException("Product not available"));

        restService.updateProduct(MockDataConstants.sonyProduct.getIdentifier(), mockPrice);

    }


    private void setMockTestData() {
        product = new Product(13860428, new Amount(15.88d, "USD"));
        mockProd = new ProductDetail();
        mockProd.setIdentifier(13860428);
        mockProd.setTitle("Sony Bravia TV");

        mockPrice = new ProductDetailPrice();
        mockPrice.setAmount(15.88d);
        mockPrice.setCurrencyCode("USD");
        mockProd.setPrice(mockPrice);

        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        Enumeration<String> headerNames = Collections.enumeration(headers.keySet());
        httpServletRequestMock = PowerMockito.mock(HttpServletRequest.class);
        PowerMockito.when(httpServletRequestMock.getHeaderNames()).thenReturn(headerNames);
    }

    private void setMockMethodActions() throws Exception {

        PowerMockito.when(productProcessorMock.getProductDetails(13860428)).thenReturn(mockProd);
    }
}