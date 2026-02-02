package org.acme.service;

import java.util.List;

import org.acme.model.Race;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
@Named
public class RaceService {

    @Inject
    EntityManager em;

    
    public List<Race> findAll() {
        return em.createQuery("SELECT r FROM Race r", Race.class).getResultList();
    }


}
