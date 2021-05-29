package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        logger.info("Startup Listener");

        ProductRepository productRepository = new ProductRepository();

        productRepository.save(new Product(null, "product1", "description1", new BigDecimal(100)));
        productRepository.save(new Product(null, "product2", "description2", new BigDecimal(200)));
        productRepository.save(new Product(null, "product3", "description3", new BigDecimal(300)));

        sce.getServletContext().setAttribute("productRepository", productRepository);
    }
}
