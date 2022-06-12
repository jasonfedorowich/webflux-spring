package com.webflux.webfluxspring.controllers;

import com.webflux.webfluxspring.models.Category;
import com.webflux.webfluxspring.models.Vendor;
import com.webflux.webfluxspring.repository.CategoryRepository;
import com.webflux.webfluxspring.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class VendorControllerTest {
    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
        Map<Integer, Integer> map = new HashMap<>();


    }

    @Test
    void getVendors() {
        when(vendorRepository.findAll()).thenReturn(Flux.just(Vendor.builder().firstName("Cat1").build(),
                Vendor.builder().firstName("Cat2").build()));

        webTestClient.get()
                .uri("/api/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void findVendor() {
        when(vendorRepository.findById(anyString())).thenReturn(Mono.just(Vendor.builder().firstName("Cat1").build()
        ));

        webTestClient.get()
                .uri("/api/vendors/1")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void create(){
        when(vendorRepository.saveAll(any(Publisher.class)))
                .thenReturn(Flux.just(Vendor.builder().build()));

        webTestClient.post()
                .uri("/api/vendors/1")
                .body(Flux.just(Vendor.builder().build()), Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update(){
        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(Mono.just(Vendor.builder().build()));


        webTestClient.put()
                .uri("/api/vendors/1")
                .body(Mono.just(Vendor.builder().build()), Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}