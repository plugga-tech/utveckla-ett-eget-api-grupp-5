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

    public void createUser(UserDto userDto) throws PasswordException, IllegalArgumentException {

        if (!verifyPassword(userDto.getPassword())) {
            throw new PasswordException(
                    "Password is not strong enough. Use at least 1 uppercase letter and one lowercase letter");
        }
        if (!verifyUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Invalid format on username. Username must be between 4-30 characters.");
        }
        if (userExists(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists. Please choose another one.");

        }

        User user = new User()
                .setUsername(userDto.getUsername())
                .setPassword(BcryptUtil.bcryptHash(userDto.getPassword()))
                .setApiKey(UUID.randomUUID().toString())
                .setRole("user");

        user.persist();
    }

    // Kontrollerar om ett användarnamn redan är registrerat i databasen.
    // Returnerar true om användaren finns, annars false.
    private boolean userExists(String username) {

        return !em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList()
                .isEmpty();

    }

    // Hämtar specifik användares API key för att returnera till frontend
    public String fetchApiKey(String username, String password) {

        User user = returnUser(username);

        if (!BcryptUtil.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return user.getApiKey();

    }

    private User returnUser(String username) {
        try {
            return em
                    .createQuery("SELECT u FROM User u Where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            throw new IllegalArgumentException("User not found: " + username);
        }
    }

    private boolean verifyUsername(String username) {
        if (username.length() < 4 || username.length() > 30) {
            return false;
        }
        return true;
    }

    private boolean verifyPassword(String password) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).{4,50}$");
        return passwordPattern.matcher(password).matches();

    }
}
