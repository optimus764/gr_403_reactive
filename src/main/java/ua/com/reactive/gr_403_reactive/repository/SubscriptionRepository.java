package ua.com.reactive.gr_403_reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ua.com.reactive.gr_403_reactive.entity.Subscription;

public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Long> {

    // Spring сам складе запит: SELECT * FROM subscriptions WHERE reader_id = ?
    Flux<Subscription> findByReaderId(Long readerId);
}