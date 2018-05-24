package com.myretailproduct.service.controller;


import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
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
    public void testGetProduct() throws ProductInformationNotAvailableException {

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


    @Ignore
    public void testGetProduct_InvalidProduct() throws ProductInformationNotAvailableException {

        ProductDetail mockProd = null;
        PowerMockito.when(productProcessorMock.getProductDetails(13860428)).thenReturn(mockProd);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Enumeration<String> headerNames = Collections.enumeration(headers.keySet());
        HttpServletRequest httpServletRequestMock = PowerMockito.mock(HttpServletRequest.class);
        PowerMockito.when(httpServletRequestMock.getHeaderNames()).thenReturn(headerNames);
        PowerMockito.when(httpServletRequestMock.getHeader("Content-Type")).thenReturn("application/json");

        ResponseEntity<ProductDetail> response = null;//mockService.getProduct(13860428, httpServletRequestMock);
        Assert.assertNotNull(response);
        Assert.assertNull(response.getBody());


    }


    private void setMockTestData() {
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