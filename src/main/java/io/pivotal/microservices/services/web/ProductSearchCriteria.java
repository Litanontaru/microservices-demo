package io.pivotal.microservices.services.web;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * @author Andrei_Yakushin
 * @since 8/24/2016 11:03 AM
 */
public class ProductSearchCriteria {
    private String productNumber;

    private String searchText;

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isValid() {
        if (StringUtils.hasText(productNumber)) {
            return !(StringUtils.hasText(searchText));
        } else {
            return (StringUtils.hasText(searchText));
        }
    }

    public boolean validate(Errors errors) {
        if (StringUtils.hasText(productNumber)) {
            if (productNumber.length() != 9)
                errors.rejectValue("productNumber", "badFormat", "Product number should be 9 digits");
            else {
                try {
                    Integer.parseInt(productNumber);
                } catch (NumberFormatException e) {
                    errors.rejectValue("productNumber", "badFormat", "Product number should be 9 digits");
                }
            }
            if (StringUtils.hasText(searchText)) {
                errors.rejectValue("searchText", "nonEmpty", "Cannot specify Product number and search text");
            }
        } else if (StringUtils.hasText(searchText)) {
            ; // Nothing to do
        } else {
            errors.rejectValue("productNumber", "nonEmpty", "Must specify either an product number or search text");
        }

        return errors.hasErrors();
    }

    @Override
    public String toString() {
        return (StringUtils.hasText(productNumber) ? "number: " + productNumber : "")
                + (StringUtils.hasText(searchText) ? " text: " + searchText : "");
    }
}
