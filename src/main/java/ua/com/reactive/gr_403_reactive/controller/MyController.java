package ua.com.reactive.gr_403_reactive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ua.com.reactive.gr_403_reactive.entity.Client;

@RestController
public class MyController {

    @GetMapping("/clients")
    public Flux<Client> getClients() {
        Flux<Client> clients = Flux.just(
                        new Client(1L, "Yelyzaveta", "Havuka", 20),
                        new Client(2L, "Liza", "Havuka", 20),
                        new Client(3L, "Yelyzaveta", "Havuka IN-403", 20)
                )
                .skip(0)
                .take(2);

        return clients;
    }
}