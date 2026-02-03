package org.acme.model;

public class UserResponseDto {

    public String username;
    public String apiKey;

    public UserResponseDto(){
    }

    //konstruktorn som hämtar värden från User entiteten
    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.apiKey = user.getApiKey();
    }

    //Getters and Setters
    public String getUsername() {
        return username;
    }

    public UserResponseDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public UserResponseDto setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }
}