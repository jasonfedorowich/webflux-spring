package com.webflux.webfluxspring.bootstrap;

import com.webflux.webfluxspring.models.Category;
import com.webflux.webfluxspring.models.Vendor;
import com.webflux.webfluxspring.repository.CategoryRepository;
import com.webflux.webfluxspring.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;



    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0) {
            //load data
            System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

            if (categoryRepository.count().block() == 0) {
                //load data
                System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

                categoryRepository.save(Category.builder()
                        .description("Fruits").build()).block();

                categoryRepository.save(Category.builder()
                        .description("Nuts").build()).block();

                categoryRepository.save(Category.builder()
                        .description("Breads").build()).block();

                categoryRepository.save(Category.builder()
                        .description("Meats").build()).block();

                categoryRepository.save(Category.builder()
                        .description("Eggs").build()).block();

                System.out.println("Loaded Categories: " + categoryRepository.count().block());

                vendorRepository.save(Vendor.builder()
                        .firstName("Joe")
                        .lastName("Buck").build()).block();

                vendorRepository.save(Vendor.builder()
                        .firstName("Micheal")
                        .lastName("Weston").build()).block();

                vendorRepository.save(Vendor.builder()
                        .firstName("Jessie")
                        .lastName("Waters").build()).block();

                vendorRepository.save(Vendor.builder()
                        .firstName("Bill")
                        .lastName("Nershi").build()).block();

                vendorRepository.save(Vendor.builder()
                        .firstName("Jimmy")
                        .lastName("Buffett").build()).block();

                System.out.println("Loaded Vendors: " + vendorRepository.count().block());
            }
        }
    }
}