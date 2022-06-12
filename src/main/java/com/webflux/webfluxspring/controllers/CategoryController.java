package com.webflux.webfluxspring.controllers;

import com.webflux.webfluxspring.models.Category;
import com.webflux.webfluxspring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public Flux<Category> list(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> get(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> create(@RequestBody Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("/{id}")
    public Mono<Category> update(@PathVariable String id, @RequestBody Mono<Category> category){
            return category
                .map(category1 -> {
                    category1.setId(id);
                    return category1;
                }).flatMap(categoryRepository::save);
    }

    @PatchMapping("/{id}")
    public Mono<Category> patch(@PathVariable String id, @RequestBody Mono<Category> category){
        return categoryRepository.findById(id)
                .map(category1 -> {
                    category.flatMap(category2 -> {
                        if(category2.getDescription() != category1.getDescription()){
                            category2.setId(id);
                            return categoryRepository.save(category2);
                        }
                        return Mono.just(category1);
                    });
                    return category1;
                });
    }
}
