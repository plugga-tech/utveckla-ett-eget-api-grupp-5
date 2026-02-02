package org.acme.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Race {

    @Id
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid() {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid;
    }

      

}
