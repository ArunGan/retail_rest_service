package com.myretailproduct.service.processor;

import com.myretail.product.model.ProductDetail;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import com.myretailproduct.service.beans.productMasterApi.ItemInfo;
import com.myretailproduct.service.beans.productMasterApi.ProductDescription;
import com.myretailproduct.service.beans.productMasterApi.ProductDetailInfo;
import com.myretailproduct.service.beans.productMasterApi.ProductInformation;
import com.myretailproduct.service.dao.ProductDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.annotation.Bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ProductProcessorImpl.class})
public class ProductProcessorImplTest {


    ProductInformation mockProductInformation;
    ProductDetailInfo apiProduct;
    ItemInfo apiItemInfo;
    ProductDescription apiProductDesc;

    @InjectMocks
    ProductProcessorImpl processor;

    @Spy
    private ProductDAO daoMock = PowerMockito.mock(ProductDAO.class);
    @Spy
    private DataCollector dataCollectorMock = PowerMockito.mock(DataCollector.class);
    ;

    private String productURL;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        setMockTestData();
        setMockMethodActions();
    }


    @Bean
    public ProductDAO getDaoMock() {
        return daoMock;
    }

    @Test
    public void testGetProductDetails_nonNullResponse() throws Exception {

        PowerMockito.when(dataCollectorMock.getProductDetail(MockDataConstants.sonyIdentifier)).thenReturn(mockProductInformation);
        PowerMockito.when(dataCollectorMock.getProductPrice(MockDataConstants.sonyIdentifier)).thenReturn(MockDataConstants.sonyPrice);

        ProductDetail actualProductDetail = processor.getProductDetails(MockDataConstants.sonyIdentifier);

        assertNotNull(actualProductDetail);
        assertNotNull(actualProductDetail.getIdentifier());
        assertNotNull(actualProductDetail.getTitle());
        assertNotNull(actualProductDetail.getPrice());
        assertNotNull(actualProductDetail.getPrice().getAmount());
        assertNotNull(actualProductDetail.getPrice().getCurrencyCode());

    }

    @Test
    public void testGetProductDetails_expectedProduct() throws Exception {

        PowerMockito.when(dataCollectorMock.getProductDetail(MockDataConstants.sonyIdentifier)).thenReturn(mockProductInformation);
        PowerMockito.when(dataCollectorMock.getProductPrice(MockDataConstants.sonyIdentifier)).thenReturn(MockDataConstants.sonyPrice);
        ProductDetail actualProductDetail = processor.getProductDetails(MockDataConstants.sonyIdentifier);
        ProductDetail expectedProductDetails = MockDataConstants.sonyProduct;

        assertEquals(expectedProductDetails, actualProductDetail);
    }

    @Test(expected = ProductInformationNotAvailableException.class)
    public void testGetProductDetails_unAvailableProductPrice() throws Exception {

        PowerMockito.when(dataCollectorMock.getProductDetail(MockDataConstants.sonyIdentifier)).thenReturn(mockProductInformation);
        PowerMockito.when(dataCollectorMock.getProductPrice(MockDataConstants.sonyIdentifier)).thenThrow(new ProductInformationNotAvailableException("Product not available"));
        processor.getProductDetails(MockDataConstants.sonyIdentifier);

    }


    private void setMockTestData() {
        mockProductInformation = new ProductInformation();
        apiProduct = new ProductDetailInfo();
        apiItemInfo = new ItemInfo();
        apiProductDesc = new ProductDescription();
        apiProductDesc.setTitle(MockDataConstants.sonyTitle);
        apiItemInfo.setProductDescription(apiProductDesc);
        apiProduct.setItemInfo(apiItemInfo);
        mockProductInformation.setProductInfo(apiProduct);
        productURL = MockDataConstants.productUrl;
    }

    private void setMockMethodActions() throws Exception {


    }

}