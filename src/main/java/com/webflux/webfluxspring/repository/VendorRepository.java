package com.webflux.webfluxspring.repository;

import com.webflux.webfluxspring.models.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
