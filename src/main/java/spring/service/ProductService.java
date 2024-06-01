package spring.service;

import spring.dto.CreateProductDtro;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductDtro createProductDtro) throws ExecutionException, InterruptedException;
}
