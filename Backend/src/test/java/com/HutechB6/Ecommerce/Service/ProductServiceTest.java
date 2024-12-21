package com.HutechB6.Ecommerce.Service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.repository.IProductRepository;
import com.HutechB6.Ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

public class ProductServiceTest {
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    private Product product;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new Product();
        product.setName("Test");
        product.setDescription("Test Description");
        product.setPrice(1000);
        product.setQuantity(10);

    }
}
