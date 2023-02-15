package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//REPOSITORIO ORIENTADO AL DOMINIO
@Repository
public class ProductoRepository implements ProductRepository {

    //INVERSION DE CONTROL PARA QUE SPRING CREE LAS INSTANCIAS DE LOS OBJETOS
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper productMapper;

    //query method que devuelve todos los productos
    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        //Utilizamos el metodo toProducts de ProductMapper para convertir la lista de productos a product
        return productMapper.toProducts(productos);
    }

    //query method que devuelve todos los productos de una categoria ordenados por nombre
    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    //query method que devuelve todos los productos con estado activo y cantidadStock de menor cantidad
    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map(prods -> productMapper.toProducts(prods));
    }

    //query method que devuelve un producto por su id
    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto -> productMapper.toProduct(producto));
    }

    //query method que guarda un producto
    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    //query method que elimina un producto de acuerdo a su id
    @Override
    public void delete(int productId)  {
        productoCrudRepository.deleteById(productId);
    }
}
