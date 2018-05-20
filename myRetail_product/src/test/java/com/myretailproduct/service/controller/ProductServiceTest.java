package com.myretailproduct.service.controller;


import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.processor.ProductProcessorImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RunWith(value = PowerMockRunner.class)
public class ProductServiceTest {



    @Mock
    ProductProcessorImpl productProcessorImpl = null;

    @InjectMocks
    ProductService mockService = null;

    @Before
    public void setUp(){
        mockService = new ProductService();

        ReflectionTestUtils.setField(mockService, "productProcessor", productProcessorImpl);

    }
    @Test
    public void testGetProduct() throws ProductInformationNotAvailableException {

        ProductDetail mockProd = new ProductDetail();
        mockProd.setIdentifier(13860428);
        mockProd.setTitle("Sony Bravia TV");

        ProductDetailPrice mockPrice = new ProductDetailPrice();
        mockPrice.setAmount(15.88d);
        mockPrice.setCurrencyCode("USD");
        mockProd.setPrice(mockPrice);

        Mockito.when(productProcessorImpl.getProductDetails(13860428)).thenReturn(mockProd);


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Enumeration<String> headerNames = Collections.enumeration(headers.keySet());
        HttpServletRequest httpServletRequestMock = PowerMockito.mock(HttpServletRequest.class);
        PowerMockito.when(httpServletRequestMock.getHeaderNames()).thenReturn(headerNames);
        PowerMockito.when(httpServletRequestMock.getHeader("Content-Type")).thenReturn("application/json");

        ResponseEntity<ProductDetail> response  =  mockService.getProduct(13860428, httpServletRequestMock);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody() instanceof ProductDetail);

        ProductDetail actualProd = response.getBody();

        Assert.assertNotNull(actualProd.getIdentifier());
        Assert.assertEquals(actualProd.getIdentifier(), new Integer(13860428));
        Assert.assertEquals(actualProd.getTitle(),"Sony Bravia TV");



    }


    @Test
    public void testGetProduct_InvalidProduct() throws ProductInformationNotAvailableException {

        ProductDetail mockProd = null;
        Mockito.when(productProcessorImpl.getProductDetails(13860428)).thenReturn(mockProd);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Enumeration<String> headerNames = Collections.enumeration(headers.keySet());
        HttpServletRequest httpServletRequestMock = PowerMockito.mock(HttpServletRequest.class);
        PowerMockito.when(httpServletRequestMock.getHeaderNames()).thenReturn(headerNames);
        PowerMockito.when(httpServletRequestMock.getHeader("Content-Type")).thenReturn("application/json");

        ResponseEntity<ProductDetail> response  =  mockService.getProduct(13860428, httpServletRequestMock);
        Assert.assertNotNull(response);
        Assert.assertNull(response.getBody());


    }
}