package ua.com.reactive.gr_403_reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.Publication;
import ua.com.reactive.gr_403_reactive.service.PublicationService;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    public Flux<Publication> getAll() {
        return publicationService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Publication> getById(@PathVariable("id") Long id) {
        return publicationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Publication> create(@RequestBody Publication publication) {
        return publicationService.create(publication);
    }

    @PutMapping("/{id}")
    public Mono<Publication> update(@PathVariable("id") Long id, @RequestBody Publication publication) {
        return publicationService.update(id, publication);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return publicationService.delete(id);
    }
}