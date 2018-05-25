package com.myretailproduct.service;


import com.myretailproduct.service.dao.ProductDAO;
import com.myretailproduct.service.model.Amount;
import com.myretailproduct.service.model.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * Created by agane13 on 11/8/17.
 */
@SpringBootApplication
@ComponentScan("com.myretailproduct")
public class MainApplication {

    static Logger log = Logger.getLogger(MainApplication.class);
    @Autowired
    private ProductDAO repository;
    @Value("${environment}")
    private String env;

    /**
     * Main method to start spring app
     *
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
        if ("dev".equalsIgnoreCase(env)) {

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
