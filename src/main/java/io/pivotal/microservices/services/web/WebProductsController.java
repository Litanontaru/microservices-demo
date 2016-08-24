package io.pivotal.microservices.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 1:55 PM
 */
@Controller
public class WebProductsController {
    @Autowired
    protected WebProductsService productsService;

    protected Logger logger = Logger.getLogger(WebProductsController.class
            .getName());

    public WebProductsController(WebProductsService productsService) {
        this.productsService = productsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("productNumber", "searchText");
    }

    @RequestMapping("/products")
    public String goHome() {
        return "index";
    }

    @RequestMapping(value = "/products/{productNumber}", method = RequestMethod.GET)
    public String byNumber(Model model, @PathVariable("productNumber") String productNumber) {
        logger.info("web-service byNumber() invoked: " + productNumber);
        Product product = productsService.findByNumber(productNumber);
        logger.info("web-service byNumber() found: " + product);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "/products/title/{name}", method = RequestMethod.GET)
    public String byTitle(Model model, @PathVariable("name") String name) {
        logger.info("web-service byOwner() invoked: " + name);

        List<Product> products = productsService.byTitleContains(name);
        logger.info("web-service byOwner() found: " + products);
        model.addAttribute("search", name);
        if (products != null)
            model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new ProductSearchCriteria());
        return "productSearch";
    }

    @RequestMapping(value = "/products/dosearch")
    public String doSearch(Model model, ProductSearchCriteria criteria, BindingResult result) {
        logger.info("web-service search() invoked: " + criteria);
        criteria.validate(result);
        if (result.hasErrors()) {
            return "productSearch";
        }
        String accountNumber = criteria.getProductNumber();
        if (StringUtils.hasText(accountNumber)) {
            return byNumber(model, accountNumber);
        } else {
            String searchText = criteria.getSearchText();
            return byTitle(model, searchText);
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String create(Model model, @RequestBody Product product) {
        logger.info("web-service create() invoked: " + product);
        product = productsService.create(product);
        logger.info("web-service created: " + product);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public String update(Model model, @RequestBody Product product) {
        logger.info("web-service create() invoked: " + product);
        productsService.update(product);
        logger.info("web-service created: " + product);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "/products/{productNumber}", method = RequestMethod.DELETE)
    public String delete(Model model, @PathVariable("productNumber") String productNumber) {
        logger.info("web-service delete() invoked: " + productNumber);
        productsService.delete(productNumber);
        logger.info("web-service delete: " + productNumber);
        return searchForm(model);
    }
}
