package io.pivotal.microservices.products;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 11:41 AM
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    /**
     * Find an product with the specified product number.
     *
     * @param productNumber
     * @return The product if found, null otherwise.
     */
    public Product findByNumber(String productNumber);

    /**
     * Find products whose title contains the specified string
     *
     * @param partialName
     *            Any alphabetic string.
     * @return The list of matching products - always non-null, but may be
     *         empty.
     */
    public List<Product> findByTitleContainingIgnoreCase(String partialName);

    /**
     * Fetch the number of products known to the system.
     *
     * @return The number of products.
     */
    @Query("SELECT count(*) from Product")
    public int countProducts();
}
