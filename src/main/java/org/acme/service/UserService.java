package org.acme.service;

import java.util.UUID;
import java.util.regex.Pattern;

import org.acme.model.User;
import org.acme.model.UserDto;
import org.bouncycastle.openssl.PasswordException;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class UserService {


    @Inject
    EntityManager em;
    public void createUser(UserDto userDto) throws PasswordException, IllegalArgumentException  {

        if (!verifyPassword(userDto.getPassword())) {
            throw new PasswordException("Password is not strong enough. Use at least 1 uppercase letter and one lowercase letter");
        }
        if (!verifyUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Invalid format on username. Username must be between 4-30 characters.");
        }
   
        User user = new User()
            .setUsername(userDto.getUsername())
            .setPassword(BcryptUtil.bcryptHash(userDto.getPassword()))
            .setApiKey()
            .setRole();

            user.persist();
    }


    // Hämtar specifik användares API key för att returnera till frontend 
    public String fetchApiKey(UserDto userDto){

        try {
       User user = (User) em.createQuery("SELECT u FROM User u Where u.username = " + userDto.getUsername());
        return user.getApiKey();
        } catch (NoResultException e) {
            throw new IllegalArgumentException("User not found: " + userDto.getUsername());
        }

    }

    private boolean verifyUsername(String username){
        if (username.length() < 4 || username.length() > 30){
            return false;
        } 
        return true;
    }

    private boolean verifyPassword(String password){

        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).{4,50}$");
        
        return passwordPattern.matcher(password).matches();

    }

    public void loginUser(UserDto userDto) {

        User user = (User) em.createQuery("SELECT u FROM User u Where u.username = " + userDto.getUsername());

        if (
            !userDto.getUsername().equals(user.getUsername()) || 
            !BcryptUtil.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException();
        }
        

    }


}


