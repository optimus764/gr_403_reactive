package ua.com.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.Reader;
import ua.com.reactive.gr_403_reactive.repository.ReaderRepository;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    // READ: усі читачі
    public Flux<Reader> findAll() {
        return readerRepository.findAll();
    }

    // READ: один читач за id (якщо немає - помилка 404)
    public Mono<Reader> findById(Long id) {
        return readerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reader " + id + " not found")));
    }

    // CREATE
    public Mono<Reader> create(Reader reader) {
        reader.setId(null);
        return readerRepository.save(reader);
    }

    // UPDATE
    public Mono<Reader> update(Long id, Reader reader) {
        return findById(id).flatMap(existing -> {
            reader.setId(existing.getId());
            return readerRepository.save(reader);
        });
    }

    // DELETE
    public Mono<Void> delete(Long id) {
        return findById(id).flatMap(readerRepository::delete);
    }
}