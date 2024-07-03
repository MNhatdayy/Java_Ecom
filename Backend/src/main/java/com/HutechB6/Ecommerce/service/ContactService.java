package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.Contact;
import com.HutechB6.Ecommerce.repository.IContactRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {
    private final IContactRepository contactRepository;
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact addContact(Contact contact) {
       return contactRepository.save(contact);
    }

    public void updateContact(@NotNull Contact contact) {
        Contact existingContact = contactRepository.findById(contact.getId())
                .orElseThrow(() -> new IllegalStateException("Contact with ID " + contact.getId() + " does not exist."));
        existingContact.setName(contact.getName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhone(contact.getPhone());
        existingContact.setDescription(contact.getDescription());
        contactRepository.save(existingContact);
    }

    public void deleteContactById(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new IllegalStateException("Contact with ID " + id + " does not exist.");
        }
        contactRepository.deleteById(id);
    }
}
