package ua.com.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("publications")
public class Publication {

    @Id
    private Long id;
    private String title;             // назва видання
    private String category;          // тематика
    private String publisher;         // видавництво
    private BigDecimal pricePerMonth; // колонка price_per_month
}