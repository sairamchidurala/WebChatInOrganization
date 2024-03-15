package com.example.sms.dto;

public class UsersDTO {
    private String userId;
    private String emailId;
    private String firstName;
    private String lastName;

    public UsersDTO() {
    }

    public UsersDTO(String userId, String emailId, String firstName, String lastName) {
        this.userId = userId;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
