package spring.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import spring.dto.CreateProductDtro;
import spring.event.ProductCreatedEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String createProduct(CreateProductDtro createProductDtro) {
        //TODO save to DB

        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                createProductDtro.getTitle(),
                createProductDtro.getPrice(),
                createProductDtro.getQuantity());

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate
                .send("product-createdJava-events-topic", productId, productCreatedEvent);

        future.whenComplete((res, ex) -> {
            if(ex != null){
                logger.error("Failed to send massage: {}", ex.getMessage());
            } else {
                logger.info("Message send successfully: {}", res.getRecordMetadata());
            }
        });

        logger.info("Return: {}", productId);
        return productId;
    }
}
