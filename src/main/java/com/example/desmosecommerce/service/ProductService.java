package com.example.desmosecommerce.service;

import com.example.desmosecommerce.dao.ProductMapper;
import com.example.desmosecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractCrudService<Product, Long> {
    
    private final ProductMapper productMapper;
    
    @Autowired
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    
    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
    
    @Override
    public Product findById(Long id) {
        return productMapper.findById(id);
    }
    
    @Override
    protected void save(Product product) {
        productMapper.insert(product);
    }
    
    @Override
    protected void update(Product product) {
        productMapper.update(product);
    }
    
    @Override
    protected void delete(Long id) {
        productMapper.delete(id);
    }
    
    @Override
    protected Long getEntityId(Product product) {
        return product.getId();
    }
    
    public List<Product> searchByName(String searchTerm) {
        return productMapper.searchByName(searchTerm);
    }
    
    public void updateStock(Long productId, Integer quantity) {
        productMapper.updateStock(productId, quantity);
    }
    
    @Override
    protected void validateEntity(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
    }
    
    public void updateProduct(Product product) {
        productMapper.update(product);
    }
    
    public List<Product> searchByCriteria(SearchCriteria criteria) {
        return findAll().stream().filter(criteria::matches).collect(Collectors.toList());
    }
} 