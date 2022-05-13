package com.spr.votingsystem.controller;

import com.spr.votingsystem.model.Seat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class SeatController {

    private final EntityManagerFactory factory;
    private EntityManager manager;

    public SeatController() {
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

    public Integer addSeat(Integer number_contesters) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        Integer seatID = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Seat seat = new Seat();
            seat.setNumber_contesters(number_contesters);
            manager.persist(seat);
            tx.commit();
            seatID = seat.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return seatID;
    }

    public List<Seat> listSeats() {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;
        List<Seat> allSeats = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            allSeats = manager.createQuery("select s from Seat s", Seat.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
        return allSeats;
    }

    public void updateSeat(Integer seatID, Integer number_contesters) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Seat seat = manager.find(Seat.class, seatID);
            seat.setNumber_contesters(number_contesters);
            manager.merge(seat);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void deleteSeat(Integer seatID) {
        manager = factory.createEntityManager();
        EntityTransaction tx = null;

        try {
            tx = manager.getTransaction();
            tx.begin();
            Seat seat = manager.find(Seat.class, seatID);
            manager.remove(seat);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
