package io.pivotal.microservices.services.web;

import io.pivotal.microservices.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 1:55 PM
 */
@Service
public class WebProductsService {
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebAccountsService.class
            .getName());

    public WebProductsService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
                : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is "
                + restTemplate.getRequestFactory().getClass());
    }

    public Product findByNumber(String productNumber) {
        logger.info("findByNumber() invoked: for " + productNumber);
        return restTemplate.getForObject(serviceUrl + "/products/{number}", Product.class, productNumber);
    }

    public List<Product> byTitleContains(String name) {
        logger.info("byTitleContains() invoked:  for " + name);
        Product[] products = null;
        try {
            products = restTemplate.getForObject(serviceUrl + "/products/title/{name}", Product[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }
        if (products == null || products.length == 0) {
            return null;
        } else {
            return Arrays.asList(products);
        }
    }

    public Product getByNumber(String productNumber) {
        Product product = restTemplate.getForObject(serviceUrl + "/products/{number}", Product.class, productNumber);
        if (product == null) {
            throw new ProductNotFoundException(productNumber);
        } else {
            return product;
        }
    }

    public Product create(Product product) {
        Product persisted = restTemplate.postForObject(serviceUrl + "/products", product, Product.class);
        if (product == null) {
            throw new ProductNotFoundException(persisted.getNumber());
        } else {
            return persisted;
        }
    }

    public void update(Product product) {
        restTemplate.put(serviceUrl + "/products", product);
    }

    public void delete(String productNumber) {
        restTemplate.delete(serviceUrl + "/products/{number}", productNumber);
    }
}
