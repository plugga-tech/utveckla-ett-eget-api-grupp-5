package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
    
    private String role;

    @Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;    
    

    public String getUsername() {
        return username;
    }
    
    public User setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public User setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }
    
    // För verifiering av password från klient, använd: BcryptUtil.matches(klientPassword, krypteratPassword)
    public String getPassword() {
        return password;
    }
    
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

   
    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }
    
}
