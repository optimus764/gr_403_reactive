package ua.com.reactive.gr_403_reactive.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.Reader;
import ua.com.reactive.gr_403_reactive.service.ReaderService;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping
    public Flux<Reader> getAll() {
        return readerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Reader> getById(@PathVariable("id") Long id) {
        return readerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Reader> create(@RequestBody Reader reader) {
        return readerService.create(reader);
    }

    @PutMapping("/{id}")
    public Mono<Reader> update(@PathVariable("id") Long id, @RequestBody Reader reader) {
        return readerService.update(id, reader);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return readerService.delete(id);
    }
}