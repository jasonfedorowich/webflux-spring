package com.webflux.webfluxspring.controllers;

import com.webflux.webfluxspring.models.Category;
import com.webflux.webfluxspring.models.Vendor;
import com.webflux.webfluxspring.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController{

    private final VendorRepository vendorRepository;

    @GetMapping
    public Flux<Vendor> getVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> findVendor(@RequestParam String id){
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Vendor> create(@RequestBody Publisher<Vendor> publisher){
        return vendorRepository.saveAll(publisher);
    }

    @PutMapping("/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Mono<Vendor> vendorMono){
        return vendorMono
                .map(vendor -> {
                    vendor.setId(id);
                    return vendor;
                }).flatMap(vendorRepository::save);
    }

    @PatchMapping("/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Mono<Vendor> vendor){
        return vendorRepository.findById(id)
                .map(vendor1 -> {
                    vendor.flatMap(vendor2 -> {
                        if(vendor1.getFirstName() != vendor2.getFirstName() || vendor2.getLastName() != vendor1.getLastName()){
                            vendor2.setId(id);
                            return vendorRepository.save(vendor2);
                        }
                        return Mono.just(vendor1);
                    });
                    return vendor1;
                });
    }
}
