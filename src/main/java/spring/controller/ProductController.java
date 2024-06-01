package spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.CreateProductDtro;
import spring.error.ErrorMessage;
import spring.service.ProductServiceImpl;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductDtro createProductDtro){
        String productId = null;
        try {
            productId = productService.createProduct(createProductDtro);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(), e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

}
