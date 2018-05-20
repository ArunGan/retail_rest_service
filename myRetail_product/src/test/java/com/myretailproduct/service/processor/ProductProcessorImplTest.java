package com.myretailproduct.service.processor;

import com.myretail.product.model.ProductDetail;
import com.myretail.product.model.ProductDetailPrice;
import com.myretailproduct.service.Exception.InvalidProductException;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.beans.Amount;
import com.myretailproduct.service.beans.productMasterApi.Item;
import com.myretailproduct.service.beans.productMasterApi.Product;
import com.myretailproduct.service.beans.productMasterApi.ProductDescription;
import com.myretailproduct.service.beans.productMasterApi.ProductDetailResponse;
import com.myretailproduct.service.dao.ProductDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ProductProcessorImpl.class})
public class ProductProcessorImplTest {


    ProductDetailResponse apiProductResponse;
    @InjectMocks
    ProductProcessorImpl processor;
    @Mock
    private ProductDAO daoMock;
    @Mock
    private RestTemplate templateMock;
    private String productURL = "http://redsky.target.com/v2/pdp/tcin/{product_identifier}?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        apiProductResponse = new ProductDetailResponse();
        Product apiProduct = new Product();
        Item apiItem = new Item();
        ProductDescription apiProductDesc = new ProductDescription();
        apiProductDesc.setTitle("The Big Lebowski (Blu-ray)");
        apiItem.setProductDescription(apiProductDesc);
        apiProduct.setItem(apiItem);
        apiProductResponse.setProduct(apiProduct);


    }

    @Bean
    public ProductDAO getDaoMock() {
        return daoMock;
    }

    @Test
    public void testGetProductDetails() throws ProductInformationNotAvailableException {


        templateMock = Mockito.mock(RestTemplate.class);
        Mockito.when(templateMock.getForObject(Matchers.anyString(), Matchers.eq(ProductDetailResponse.class))).thenReturn(apiProductResponse);
        //PowerMockito.when(ConnectionUtil.getRestTemplate()).thenReturn(mockTemplate);
        //PowerMockito.when(mockTemplate.getForObject("http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics",
        //        ProductDetailResponse.class)).thenReturn(apiProductResponse);
        com.myretailproduct.service.beans.Product mockProduct = new com.myretailproduct.service.beans.Product(13860428, new Amount(13.99d, "USD"));
        daoMock = Mockito.mock(ProductDAO.class);
        Mockito.when(daoMock.findByProductIdentifier(13860428)).thenReturn(mockProduct);
        processor = new ProductProcessorImpl();

        ReflectionTestUtils.setField(processor, "productDAO", daoMock);
        ReflectionTestUtils.setField(processor, "productURL", productURL);
        ReflectionTestUtils.setField(processor, "restTemplate", templateMock);

        ProductDetail actualProductDetail = processor.getProductDetails(13860428);

        assertNotNull(actualProductDetail);
        assertNotNull(actualProductDetail.getIdentifier());
        assertNotNull(actualProductDetail.getTitle());
        assertNotNull(actualProductDetail.getPrice());
        assertNotNull(actualProductDetail.getPrice().getAmount());
        assertNotNull(actualProductDetail.getPrice().getCurrencyCode());

        assertEquals(actualProductDetail.getIdentifier(), new Integer(13860428));
        assertEquals(actualProductDetail.getTitle(), "The Big Lebowski (Blu-ray)");


    }

    @Test(expected = ProductInformationNotAvailableException.class)
    public void testGetProductDetails_NoProductFound() throws ProductInformationNotAvailableException {

        templateMock = Mockito.mock(RestTemplate.class);
        Mockito.when(templateMock.getForObject(Matchers.anyString(), Matchers.eq(ProductDetailResponse.class))).thenReturn(null);
        daoMock = Mockito.mock(ProductDAO.class);
        Mockito.when(daoMock.findByProductIdentifier(1386042)).thenReturn(null);
        processor = new ProductProcessorImpl();

        ReflectionTestUtils.setField(processor, "productDAO", daoMock);
        ReflectionTestUtils.setField(processor, "productURL", productURL);
        ReflectionTestUtils.setField(processor, "restTemplate", templateMock);

        processor.getProductDetails(1386042);

    }

    @Test(expected = ProductInformationNotAvailableException.class)
    public void testGetProductDetails_NoPriceFound() throws ProductInformationNotAvailableException {

        templateMock = Mockito.mock(RestTemplate.class);
        Mockito.when(templateMock.getForObject(Matchers.anyString(), Matchers.eq(ProductDetailResponse.class))).thenReturn(apiProductResponse);
        com.myretailproduct.service.beans.Product mockProduct = new com.myretailproduct.service.beans.Product(13860428, null);
        daoMock = Mockito.mock(ProductDAO.class);
        Mockito.when(daoMock.findByProductIdentifier(13860428)).thenReturn(mockProduct);
        processor = new ProductProcessorImpl();

        ReflectionTestUtils.setField(processor, "productDAO", daoMock);
        ReflectionTestUtils.setField(processor, "productURL", productURL);
        ReflectionTestUtils.setField(processor, "restTemplate", templateMock);
        processor.getProductDetails(13860428);
    }

    @Test
    public void updateProductPrice() throws ProductInformationNotAvailableException {
        processor = new ProductProcessorImpl();
        daoMock = Mockito.mock(ProductDAO.class);
        Mockito.when(daoMock.save(Matchers.any(com.myretailproduct.service.beans.Product.class))).thenReturn(null);
        ReflectionTestUtils.setField(processor, "productDAO", daoMock);

        ProductDetailPrice mockPrice = new ProductDetailPrice();
        mockPrice.setAmount(18.22d);
        mockPrice.setCurrencyCode("USD");

        processor.updateProductPrice(13860428, mockPrice);

    }


    @Test(expected = InvalidProductException.class)
    public void updateProductPrice_InvalidUpdate() throws ProductInformationNotAvailableException {
        processor = new ProductProcessorImpl();
        daoMock = Mockito.mock(ProductDAO.class);
        Mockito.when(daoMock.save(Matchers.any(com.myretailproduct.service.beans.Product.class))).thenReturn(null);
        ReflectionTestUtils.setField(processor, "productDAO", daoMock);

        ProductDetailPrice mockPrice = new ProductDetailPrice();
        mockPrice.setAmount(-18d);
        mockPrice.setCurrencyCode("USD");
        processor.updateProductPrice(13860428, mockPrice);


    }

}