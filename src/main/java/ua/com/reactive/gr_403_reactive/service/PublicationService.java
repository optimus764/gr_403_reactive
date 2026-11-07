package ua.com.reactive.gr_403_reactive.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.com.reactive.gr_403_reactive.entity.Publication;
import ua.com.reactive.gr_403_reactive.repository.PublicationRepository;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;

    // READ: увесь каталог видань
    public Flux<Publication> findAll() {
        return publicationRepository.findAll();
    }

    // READ: одне видання за id
    public Mono<Publication> findById(Long id) {
        return publicationRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publication " + id + " not found")));
    }

    // CREATE: адміністратор додає видання до каталогу
    public Mono<Publication> create(Publication publication) {
        publication.setId(null);
        return publicationRepository.save(publication);
    }

    // UPDATE
    public Mono<Publication> update(Long id, Publication publication) {
        return findById(id).flatMap(existing -> {
            publication.setId(existing.getId());
            return publicationRepository.save(publication);
        });
    }

    // DELETE
    public Mono<Void> delete(Long id) {
        return findById(id).flatMap(publicationRepository::delete);
    }
}