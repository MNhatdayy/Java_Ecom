package com.HutechB6.Ecommerce.DTO;

import com.HutechB6.Ecommerce.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    // Add other required fields and their getters/setters

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        // Initialize other fields
    }
}
