package com.spr.votingsystem.controller;

import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Seat;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.*;

@Stateless
public class ElectionController implements ElectionLocal {

    @PersistenceContext
    private EntityManager manager;

    public Election getElectionById(Integer id) {
        return manager.find(Election.class, id);
}

    public Election addElection(String name, Date date, List<Integer> seat_numbers) {
        Set<Seat> seats = new HashSet<>();
        Election election = new Election();
        election.setName(name);
        election.setDate(date);
        election.setNumber_seats(seat_numbers.size());
        election.setCompleted(false);
        for (Integer seat_number: seat_numbers) {
            Seat new_seat = new Seat();
            new_seat.setNumber_contesters(seat_number);
            seats.add(new_seat);
        }
        election.setSeats(seats);
        manager.persist(election);
        return election;
    }

    public Election addElection(String name, Date date, Integer num_seats) {
        Set<Seat> seats = new HashSet<>();
        Election election = new Election();
        election.setName(name);
        election.setDate(date);
        election.setNumber_seats(num_seats);
        election.setCompleted(false);
        election.setSeats(seats);
        for (int i = 0; i < num_seats; i++) {
            Seat new_seat = new Seat();
            new_seat.setNumber_contesters(2);
//                seats.add(new_seat);
            election.addSeatChild(new_seat);
        }
        manager.persist(election);
        return election;
    }

    public Election addCandidateToElection(Integer candidateID, Integer electionID) {
        Election election = manager.find(Election.class, electionID);
        Set<Candidate> candidateSet = election.getCandidates();
        Candidate foundCandidate = manager.find(Candidate.class, candidateID);
        candidateSet.add(foundCandidate);
        election.setCandidates(candidateSet);
//            manager.persist(candidate);
        return manager.merge(election);
    }

    public Election deleteCandidateFromElection(Integer candidateID, Integer electionID) {
        Election election = manager.find(Election.class, electionID);
        Set<Candidate> candidateSet = election.getCandidates();
        candidateSet.removeIf(candidate -> candidate.getId() == candidateID);
        election.setCandidates(candidateSet);
        return manager.merge(election);
    }

    public List<Election> listElections() {
        return manager.createQuery("select e from Election e", Election.class)
                .getResultList();
    }

    public List<Election> listElectionsBy(String field, Object value) {
        return manager.createQuery("select e from Election e." + field + " = :value1", Election.class)
                .setParameter("value1", value)
                .getResultList();
    }

    public Election endElection(Integer electionID) {
        Election election = manager.find(Election.class, electionID);
        election.setCompleted(true);
        return manager.merge(election);
    }

    public void deleteElection(Integer electionID) {
        Election election = manager.find(Election.class, electionID);
        manager.remove(election);
    }
}
