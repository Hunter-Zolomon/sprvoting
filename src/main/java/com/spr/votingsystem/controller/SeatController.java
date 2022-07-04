package com.spr.votingsystem.controller;

import com.spr.votingsystem.interfaces.SeatLocal;
import com.spr.votingsystem.model.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Stateless
public class SeatController implements SeatLocal {

    @PersistenceContext
    private EntityManager manager;

    public Seat addSeat(Integer number_contesters) {
        Seat seat = new Seat();
        seat.setNumber_contesters(number_contesters);
        manager.persist(seat);
        return seat;
    }

    public Seat addCandidate(Integer seatID, Integer candidateID) {
        Seat seat = manager.find(Seat.class, seatID);
        Set<Candidate> contesters = seat.getContesters();
        Set<Vote> votes = seat.getVotes();
        Candidate retrievedCand = manager.find(Candidate.class, candidateID);
        contesters.add(retrievedCand);
        Vote newVote = new Vote();
        newVote.setCandidate(retrievedCand);
        newVote.setVoteCount(0);
//            votes.add(newVote);
        seat.setContesters(contesters);
        seat.addVoteChild(newVote);
//            seat.setVotes(votes);
//            retrievedCand.setVotes(votes);
        return manager.merge(seat);
    }

    public Seat deleteCandidate(Integer seatID, Integer candidateID) {
        Seat seat = manager.find(Seat.class, seatID);
        Set<Candidate> contesters = seat.getContesters();
        Set<Vote> votes = seat.getVotes();
        contesters.removeIf(candidate -> candidate.getId() == candidateID);
        votes.removeIf(vote -> vote.getCandidate().getId() == candidateID);
        seat.setContesters(contesters);
        seat.setVotes(votes);
        return manager.merge(seat);
    }

    public Seat getSeatById(Integer id) {
        return manager.find(Seat.class, id);
    }

    public List<Seat> listSeats() {
        return manager.createQuery("select s from Seat s", Seat.class).getResultList();
    }

    public Seat updateSeat(Integer seatID, Integer number_contesters) {
        //TODO check existing candidates before allowing update
        Seat seat = manager.find(Seat.class, seatID);
        seat.setNumber_contesters(number_contesters);
        return manager.merge(seat);
    }

    public void deleteSeat(Integer seatID) {
        Seat seat = manager.find(Seat.class, seatID);
        manager.remove(seat);
    }
}
