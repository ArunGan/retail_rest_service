package com.myretailproduct.service.Exception.Handler;

import com.myretail.product.model.GlobalResponse;
import com.myretail.product.model.ProductDetailResponse;
import com.myretailproduct.service.Exception.InvalidProductException;
import com.myretailproduct.service.Exception.ProductInformationNotAvailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;

/**
 * Handles the Exception within application
 */
@ControllerAdvice
public class ProductExceptionHandler {

    /**
     * Method to handle ProductInformationNotAvailableException
     * @param exception
     * @return
     */
    @ExceptionHandler(ProductInformationNotAvailableException.class)
    public ResponseEntity<String> handleProductInformationNotAvailableException(ProductInformationNotAvailableException exception) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<String>(null, headers, HttpStatus.NOT_FOUND);
    }

    /**
     * Method to handle InvalidProductException
     * @param exception
     * @return
     */
    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<ProductDetailResponse> handleInvalidProductException(InvalidProductException exception) {
        HttpHeaders headers = new HttpHeaders();

        ProductDetailResponse response = new ProductDetailResponse();
        GlobalResponse globalResponse = new GlobalResponse();
        globalResponse.setExplanationCode("RT300");
        globalResponse.setExplanationMessage(exception.getMessage());

        response.setGlobalResponse(globalResponse);

        return new ResponseEntity<ProductDetailResponse>(response, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to handle ResourceAccessException
     * @param exception
     * @return
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<GlobalResponse> handleResourceAccessException(ResourceAccessException exception) {
        HttpHeaders headers = new HttpHeaders();

        GlobalResponse globalResponse = new GlobalResponse();

        globalResponse.setResponseTimestamp(new Date());
        globalResponse.setExplanationCode("RT200");
        globalResponse.setExplanationMessage("Unable to access redsky resource");

        return new ResponseEntity<GlobalResponse>(globalResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to handle any run time exception
     * @param exception
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalResponse> handleRuntimeException(RuntimeException exception) {
        HttpHeaders headers = new HttpHeaders();

        GlobalResponse globalResponse = new GlobalResponse();

        globalResponse.setResponseTimestamp(new Date());
        globalResponse.setExplanationCode("RT201");
        globalResponse.setExplanationMessage("Application error while handling request");

        return new ResponseEntity<GlobalResponse>(globalResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
