package com.example.springwebservie.service;

import com.example.springwebservie.entity.Product;
import com.example.springwebservie.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;

@Component(value = "productService")
@WebService
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @WebMethod
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @WebMethod
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @WebMethod
    public boolean sellProduct(int productId, int quantity)  {
        Optional<Product> productOptional = productRepository.findById(productId);
        //check exist
        if(!productOptional.isPresent()) {
            return false;
        }
        Product product = productOptional.get();
        int currentQty = product.getQuantity();
        //check qty
        if(currentQty < quantity) {
            return false;
        }
        //update
        product.setQuantity(currentQty - quantity);
        productRepository.save(product);
        return true;
    }
}
