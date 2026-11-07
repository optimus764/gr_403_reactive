package ua.com.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.Subscription;
import ua.com.reactive.gr_403_reactive.repository.SubscriptionRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ReaderService readerService;
    private final PublicationService publicationService;

    // READ: усі передплати
    public Flux<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    // READ: одна передплата за id
    public Mono<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Subscription " + id + " not found")));
    }

    // READ: усі передплати одного читача
    public Flux<Subscription> findByReader(Long readerId) {
        return readerService.findById(readerId)
                .thenMany(subscriptionRepository.findByReaderId(readerId));
    }

    // CREATE: читач оформлює передплату, система рахує суму
    public Mono<Subscription> create(Subscription subscription) {
        subscription.setId(null);
        return prepare(subscription).flatMap(subscriptionRepository::save);
    }

    // UPDATE: при зміні передплати сума перераховується
    public Mono<Subscription> update(Long id, Subscription subscription) {
        return findById(id)
                .flatMap(existing -> {
                    subscription.setId(existing.getId());
                    return prepare(subscription);
                })
                .flatMap(subscriptionRepository::save);
    }

    // DELETE
    public Mono<Void> delete(Long id) {
        return findById(id).flatMap(subscriptionRepository::delete);
    }

    // Перевірка даних і підрахунок суми до оплати
    private Mono<Subscription> prepare(Subscription s) {
        if (s.getReaderId() == null || s.getPublicationId() == null) {
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "readerId and publicationId are required"));
        }
        if (s.getMonths() <= 0) {
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "months must be greater than 0"));
        }
        if (s.getStartDate() == null) {
            s.setStartDate(LocalDate.now());
        }
        return readerService.findById(s.getReaderId())                 // читач існує?
                .then(publicationService.findById(s.getPublicationId())) // видання існує?
                .map(publication -> {
                    s.calculateTotal(publication.getPricePerMonth());      // ціна × місяці
                    return s;
                });
    }
}