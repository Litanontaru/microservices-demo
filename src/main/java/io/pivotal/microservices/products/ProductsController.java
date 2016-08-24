package io.pivotal.microservices.products;

import java.util.List;
import java.util.logging.Logger;

import io.pivotal.microservices.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 11:54 AM
 */
@RestController
public class ProductsController {
    protected Logger logger = Logger.getLogger(ProductsController.class.getName());
    ProductRepository productRepository;

    @Autowired
    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;

        logger.info("ProductRepository says system has "
                + productRepository.countProducts() + " products");
    }

    @RequestMapping(value = "/products/{productNumber}", method = RequestMethod.GET)
    public Product byNumber(@PathVariable("productNumber") String productNumber) {
        logger.info("products-service byNumber() invoked: " + productNumber);
        Product product = productRepository.findByNumber(productNumber);
        logger.info("products-service byNumber() found: " + product);
        if (product == null) {
            throw new ProductNotFoundException(productNumber);
        } else {
            return product;
        }
    }

    @RequestMapping(value = "/products/title/{name}", method = RequestMethod.GET)
    public List<Product> byTitle(@PathVariable("name") String partialName) {
        logger.info("products-service byTitle() invoked: " + productRepository.getClass().getName() + " for " + partialName);
        List<Product> products = productRepository.findByTitleContainingIgnoreCase(partialName);
        logger.info("products-service byTitle() found: " + products);
        if (products == null || products.size() == 0) {
            throw new ProductNotFoundException(partialName);
        } else {
            return products;
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public void create(@RequestBody Product product) {
        logger.info("products-service create() invoked: " + product);
        Product result = productRepository.save(product);
        logger.info("products-service created: " + result);
    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public Product update(@RequestBody Product product) {
        logger.info("products-service update() invoked: " + product);
        Product result = productRepository.findByNumber(product.getNumber());
        result.setPrice(product.getPrice());
        result = productRepository.save(result);
        logger.info("products-service update: " + result);
        return result;
    }

    @RequestMapping(value = "/products/{productNumber}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("productNumber") String productNumber) {
        logger.info("products-service delete() invoked: " + productNumber);
        Product product = productRepository.findByNumber(productNumber);
        if (product != null) {
            productRepository.delete(product);
        }
        logger.info("products-service deleted: " + product);
    }
}
