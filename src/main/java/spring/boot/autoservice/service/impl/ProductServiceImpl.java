package spring.boot.autoservice.service.impl;

import org.springframework.stereotype.Service;
import spring.boot.autoservice.model.Product;
import spring.boot.autoservice.repository.ProductRepository;
import spring.boot.autoservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.getReferenceById(id);
    }
}
