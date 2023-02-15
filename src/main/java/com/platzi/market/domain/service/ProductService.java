package com.platzi.market.domain.service;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    //INYECTAMOS EL PRODUCTREPOSITORY
    @Autowired //Inicializa el objeto de acuerdo a ProductoRepository que extiende de ProductRepository
    private ProductRepository productRepository;

    //Método que objetiene toda la lista de producto
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    //Método que devuelve un Producto
    public Optional<Product> getProduct(int productId) {
        return productRepository.getProduct(productId);
    }

    public Optional<List<Product>> getByCategory(int categoryId) {
        return productRepository.getByCategory(categoryId);
    }

    //    Product save(Product product);
    public Product save(Product product) {
        return productRepository.save(product);
    }

    //void delete(int productId);
    public boolean delete(int productId) {
        //Vamos a validar si el product existe utilizando el metodo getProduct
        //Si ejecuta el map es porque si existe y retirnamos true, si no false
        return getProduct(productId).map(product -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);
    }
}
