package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired //Inversion de control para que spring cree las instancias
    private ProductService productService;

    @GetMapping()
    @ApiOperation("Get all supermarket products") //Swagger Documentation
    @ApiResponse(code = 200, message = "OK") //Swagger Documentation
    //public List<Product> getAll() {
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Search a product with an ID") //Swagger Documentation
    @ApiResponses({ //Swagger Documentation
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    //public Optional<Product> getProduct(@PathVariable("id") int productId) {
    public ResponseEntity<Product> getProduct(@ApiParam(value = "The id of the product", required = true, example = "7") //Swagger Documentation
                                                  @PathVariable("id") int productId) {
        return productService.getProduct(productId).
                map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    //public Optional<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId) {
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    //public Product save(@RequestBody Product product) {
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    //public boolean delete(@PathVariable("id") int productId) {
    public ResponseEntity delete(@PathVariable("id") int productId) {
        return new ResponseEntity(productService.delete(productId)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }

}
