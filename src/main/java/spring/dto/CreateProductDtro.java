package spring.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductDtro {
    private String title;
    private BigDecimal price;
    private Integer quantity;

}
