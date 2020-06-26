package com.centura.developer.springreact.web;

import com.centura.developer.springreact.model.Contact;
import com.centura.developer.springreact.model.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ContactController {
    private final Logger log = LoggerFactory.getLogger(ContactController.class);
    private ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/contacts")
    Collection<Contact> contacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/contact/{id}")
    ResponseEntity<?> getContact(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/contact")
    ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) throws URISyntaxException {
        log.info("Request to create contact: {}", contact);
        Contact result = contactRepository.save(contact);
        return ResponseEntity.created(new URI("/api/contact/" + result.getId()))
                .body(result);
    }

    @PutMapping("/contact/{id}")
    ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact) {
        log.info("Request to update contact: {}", contact);
        Contact result = contactRepository.save(contact);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        log.info("Request to delete contact: {}", id);
        contactRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
