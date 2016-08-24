package io.pivotal.microservices.products;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Andrei_Yakushin
 * @since 8/15/2016 11:43 AM
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.products")
@EnableJpaRepositories("io.pivotal.microservices.products")
@PropertySource("classpath:db-config.properties")
public class ProductsConfiguration {
    protected Logger logger;

    public ProductsConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }

    @Bean
    public DataSource dataSource() {
        logger.info("dataSource() invoked");

        // Create an in-memory H2 relational database containing some demo
        // products.
        DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:products-test-db/schema.sql")
                .addScript("classpath:products-test-db/data.sql").build();

        logger.info("dataSource = " + dataSource);

        // Sanity check
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> products = jdbcTemplate.queryForList("SELECT number FROM T_PRODUCT");
        logger.info("System has " + products.size() + " products");

        return dataSource;
    }
}
