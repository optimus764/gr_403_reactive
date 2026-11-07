package ua.com.reactive.gr_403_reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.Subscription;
import ua.com.reactive.gr_403_reactive.service.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public Flux<Subscription> getAll() {
        return subscriptionService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Subscription> getById(@PathVariable("id") Long id) {
        return subscriptionService.findById(id);
    }

    @GetMapping("/reader/{readerId}")
    public Flux<Subscription> getByReader(@PathVariable("readerId") Long readerId) {
        return subscriptionService.findByReader(readerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Subscription> create(@RequestBody Subscription subscription) {
        return subscriptionService.create(subscription);
    }

    @PutMapping("/{id}")
    public Mono<Subscription> update(@PathVariable("id") Long id, @RequestBody Subscription subscription) {
        return subscriptionService.update(id, subscription);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return subscriptionService.delete(id);
    }
}