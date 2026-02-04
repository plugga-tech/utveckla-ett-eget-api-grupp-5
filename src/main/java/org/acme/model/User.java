package org.acme.model;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// Om basic auth med basic authentication med jakarta persistence 
// https://quarkus.io/guides/security-getting-started-tutorial


@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntity {

    @Column(nullable = false, unique = true)
    @Username
    private String username;

    @Password
    public String password;
    
    @Roles
    public String role;

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
        this.apiKey = apiKey.toString();
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
