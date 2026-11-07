package ua.com.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long id;
    private Long subscriptionId;     // за яку передплату
    private BigDecimal amount;       // сума
    private LocalDateTime paymentDate;
    private PaymentStatus status;
}