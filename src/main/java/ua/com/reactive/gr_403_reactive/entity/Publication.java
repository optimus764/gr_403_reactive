package ua.com.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

    private Long id;
    private String title;            // назва видання
    private String category;         // тематика
    private String publisher;        // видавництво
    private BigDecimal pricePerMonth; // ціна за місяць
}