package com.HutechB6.Ecommerce.controller;


import com.HutechB6.Ecommerce.model.Contact;
import com.HutechB6.Ecommerce.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private final ContactService contactService;

    private ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAllContacts(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("Received Token: " + token);
        return contactService.getAllContact();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id)
                .orElseThrow(() -> new RuntimeException("Category not found on :: "
                        + id));
        return ResponseEntity.ok().body(contact);
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id,
                                                 @RequestBody Contact contactDetails){
        Contact contact = contactService.getContactById(id).orElseThrow(() -> new RuntimeException("Contact not found on :: "
                + id));
        contact.setName(contact.getName());
        contact.setEmail(contact.getEmail());
        contact.setPhone(contact.getPhone());
        contact.setDescription(contact.getDescription());

        final Contact updateContact = contactService.addContact(contact);
        return ResponseEntity.ok(updateContact);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found on :: "
                        + id));
        contactService.deleteContactById(id);
        return ResponseEntity.ok().build();
    }
}
