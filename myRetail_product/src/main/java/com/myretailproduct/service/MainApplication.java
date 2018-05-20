package com.myretailproduct.service;


import com.myretailproduct.service.beans.Amount;
import com.myretailproduct.service.beans.Product;
import com.myretailproduct.service.dao.ProductDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Created by agane13 on 11/8/17.
 */
@SpringBootApplication
public class MainApplication {

    @Autowired
    private ProductDAO repository;

    @Value("${environment}")
    private String env;
    static Logger log = Logger.getLogger(MainApplication.class);

    /**
     * Main method to start spring app
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    /**
     * Inserting sample data into the MongoDB database
     */
    @PostConstruct
    public void storeSampleData() {
        log.info("Begin storeSampleData");
        if ("test".equalsIgnoreCase(env)) {

            repository.deleteAll();
            repository.save(new Product(13860428, new Amount(12.33d, "USD")));
            repository.save(new Product(15117729, new Amount(14.00d, "USD")));
            repository.save(new Product(16483589, new Amount(11.00d, "USD")));
            repository.save(new Product(16696652, new Amount(19.35d, "USD")));
            repository.save(new Product(15643793, new Amount(12.99d, "USD")));

        }
        log.info("Successfully saved");
    }

}
