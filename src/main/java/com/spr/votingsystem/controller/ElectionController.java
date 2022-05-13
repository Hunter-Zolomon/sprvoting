package com.spr.votingsystem.controller;

import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.model.Seat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElectionController {

    private final EntityManagerFactory factory;
    private EntityManager manager;

    public ElectionController() {
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

    public Election getElectionById(Integer id) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Election returnElection = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            returnElection = manager.find(Election.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return returnElection;
    }

    public Integer addElection(String name, Date date, List<Integer> seat_numbers) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer electionID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            List<Seat> seats = new ArrayList<>();
            Election election = new Election();
            election.setName(name);
            election.setDate(date);
            for (Integer seat_number: seat_numbers) {
                Seat new_seat = new Seat();
                new_seat.setNumber_contesters(seat_number);
                seats.add(new_seat);
            }
            manager.persist(election);
            tx.commit();
            electionID = election.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return electionID;
    }

    public List<Election> listElections() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<Election> allElections = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allElections = manager.createQuery("select e from Election e", Election.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allElections;
    }

    public void deleteElection(Integer electionID) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Election election = manager.find(Election.class, electionID);
            manager.remove(election);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
