package com.spr.votingsystem.controller;

import com.spr.votingsystem.model.Party;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class PartyController {
    private final EntityManagerFactory factory;
    private EntityManager manager;

    public PartyController() {
        try {
            factory = Persistence.createEntityManagerFactory("default");
        } catch (Throwable ex) {
            System.err.println("Failed to create entityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void shutdown() {
        if (manager != null) manager.close();
        if (factory != null) factory.close();
    }

    public Party getPartyById(Integer id) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Party returnParty = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            returnParty = manager.find(Party.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return returnParty;
    }

    public Integer addParty(String party_name) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer partyID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Party party = new Party();
            party.setName(party_name);
            manager.persist(party);
            tx.commit();
            partyID = party.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return partyID;
    }

    public List<Party> listParties() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<Party> allParties = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allParties = manager.createQuery("select p from Party p", Party.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allParties;
    }

    public void updateParty(Integer partyID, String party_name) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Party party = manager.find(Party.class, partyID);
            party.setName(party_name);
            manager.merge(party);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void deleteParty(Integer partyID) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Party party = manager.find(Party.class, partyID);
            manager.remove(party);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
