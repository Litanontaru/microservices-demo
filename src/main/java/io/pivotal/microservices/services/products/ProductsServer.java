package io.pivotal.microservices.services.products;

import io.pivotal.microservices.products.ProductRepository;
import io.pivotal.microservices.products.ProductsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 12:45 PM
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(ProductsConfiguration.class)
public class ProductsServer {
    @Autowired
    protected ProductRepository accountRepository;

    protected Logger logger = Logger.getLogger(ProductsServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args
     *            Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Tell server to look for product-server.properties or
        // product-server.yml
        System.setProperty("spring.config.name", "product-server");

        SpringApplication.run(ProductsServer.class, args);
    }
}
