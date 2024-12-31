package com.HutechB6.Ecommerce.DTO;

public class ForgotDTO {
    private String email;
    private String newPassword;

    // Constructors
    public ForgotDTO() {}

    public ForgotDTO(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ForgotPasswordDTO{" +
                "email='" + email + '\'' +
                ", newPassword='******'" + // Hide password in logs
                '}';
    }
}

