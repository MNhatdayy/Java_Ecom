package com.HutechB6.Ecommerce.repository;


import com.HutechB6.Ecommerce.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> {

}
