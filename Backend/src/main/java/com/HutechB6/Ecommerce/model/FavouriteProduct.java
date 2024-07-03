package com.HutechB6.Ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favouriteProducts")
public class FavouriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("favouriteList")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("favouriteList")
    private User user;
}
