package com.centura.developer.springreact;

import com.centura.developer.springreact.model.Phone;
import com.centura.developer.springreact.model.Contact;
import com.centura.developer.springreact.model.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final ContactRepository repository;

    public Initializer(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Valli Kumar", "Cara Lambert", "Cody Guillory",
                "Casey Carroll").forEach(name ->
                repository.save(new Contact(name))
        );

        Contact valli = repository.findByName("Valli Kumar");
        Phone e = Phone.builder().number("(303) 790-7777")
                .build();
        valli.setPhones(Collections.singleton(e));
        repository.save(valli);

        Contact cara = repository.findByName("Cara Lambert");
        Phone e1 = Phone.builder().number("(703) 790-7897")
                .build();
        cara.setPhones(Collections.singleton(e1));
        repository.save(cara);

        repository.findAll().forEach(System.out::println);
    }
}
