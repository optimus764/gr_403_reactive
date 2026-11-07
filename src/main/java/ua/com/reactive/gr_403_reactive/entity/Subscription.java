package ua.com.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    private Long id;
    private Long readerId;           // хто передплачує
    private Long publicationId;      // яке видання
    private int months;              // на скільки місяців
    private LocalDate startDate;     // дата початку
    private BigDecimal totalAmount;  // сума до оплати

    // Система підраховує суму для оплати: ціна за місяць × кількість місяців
    public BigDecimal calculateTotal(BigDecimal pricePerMonth) {
        this.totalAmount = pricePerMonth.multiply(BigDecimal.valueOf(months));
        return this.totalAmount;
    }
}