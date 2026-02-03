package org.acme.config;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import org.acme.model.User;

import io.quarkus.runtime.StartupEvent;

@Singleton
public class Startup {
    
    
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
    
        User.deleteAll();
        User.add("admin", "admin", "admin");
        User.add("user", "user", "user");
    }

}
