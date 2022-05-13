package com.spr.votingsystem.controller;

import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Party;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class CandidateController {
    private final EntityManagerFactory factory;
    private EntityManager manager;

    public CandidateController() {
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

    public Candidate getCandidateById(Integer id) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Candidate returnCandidate = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            returnCandidate = manager.find(Candidate.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return returnCandidate;
    }

    public Integer addCandidate(String fname, String lname, String quals, Party party) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer candidateID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Candidate candidate = new Candidate();
            candidate.setFirst_name(fname);
            candidate.setLast_name(lname);
            candidate.setQualifications(quals);
            candidate.setParty(party);
            manager.persist(candidate);
            tx.commit();
            candidateID = candidate.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return candidateID;
    }

    public List<Candidate> listCandidates() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<Candidate> allCandidates = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allCandidates = manager.createQuery("select c from Candidate c", Candidate.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allCandidates;
    }

    public void updateCandidate(Integer candidateID, String fname, String lname, String quals, Party party) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Candidate candidate = manager.find(Candidate.class, candidateID);
            candidate.setFirst_name(fname);
            candidate.setLast_name(lname);
            candidate.setQualifications(quals);
            candidate.setParty(party);
            manager.merge(candidate);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void deleteCandidate(Integer candidateID) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Candidate candidate = manager.find(Candidate.class, candidateID);
            manager.remove(candidate);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
