package com.webflux.webfluxspring.controllers;

import com.webflux.webfluxspring.models.Category;
import com.webflux.webfluxspring.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CategoryControllerTest {
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        when(categoryRepository.findAll()).thenReturn(Flux.just(Category.builder().description("Cat1").build(),
                Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri("/api/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }


    @Test
    void get() {
        when(categoryRepository.findById(anyString())).thenReturn(Mono.just(Category.builder().description("Cat1").build()
               ));

        webTestClient.get()
                .uri("/api/categories/1")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void create(){
        when(categoryRepository.saveAll(any(Publisher.class)))
                .thenReturn(Flux.just(Category.builder().build()));

        webTestClient.post()
                .uri("/api/categories")
                .body(Flux.just(Category.builder().build()), Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update(){
        when(categoryRepository.save(any(Category.class)))
                .thenReturn(Mono.just(Category.builder().build()));


        webTestClient.put()
                .uri("/api/categories/1")
                .body(Mono.just(Category.builder().build()), Category.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    void patch(){
               when(categoryRepository.save(any(Category.class)))
                .thenReturn(Mono.just(Category.builder().build()));
        when(categoryRepository.findById(anyString()))
                .thenReturn(Mono.just(Category.builder().build()));


        webTestClient.patch()
                .uri("/api/categories/1")
                .body(Mono.just(Category.builder().build()), Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}