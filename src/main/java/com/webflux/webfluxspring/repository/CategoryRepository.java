package com.webflux.webfluxspring.repository;

import com.webflux.webfluxspring.models.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
