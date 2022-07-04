package com.spr.votingsystem.controller;

import com.spr.votingsystem.interfaces.VoteLocal;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Seat;
import com.spr.votingsystem.model.Vote;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Stateless
public class VoteController implements VoteLocal {

    @PersistenceContext
    private EntityManager manager;

    public Vote getVoteById(Integer id) {
        return manager.find(Vote.class, id);
    }

    public Vote addVote(Integer candidateID) {
        Candidate retrievedCand = manager.find(Candidate.class, candidateID);
        Set<Vote> votes = retrievedCand.getVotes();
        Vote retrievedVote = votes.stream()
                .filter(vote -> vote.getCandidate().getId() == candidateID)
                .findFirst()
                .get();
        retrievedVote.setVoteCount(retrievedVote.getVoteCount() + 1);
//            manager.persist(vote);
        return manager.merge(retrievedVote);
    }

    public List<Vote> listVotes() {
        return manager.createQuery("select v from Vote v", Vote.class).getResultList();
    }

    public Vote incrementVote(Integer voteID) {
        Vote vote = manager.find(Vote.class, voteID);
        vote.setVoteCount(vote.getVoteCount() + 1);
        return manager.merge(vote);
    }

    public void deleteVote(Integer voteID) {
            Vote vote = manager.find(Vote.class, voteID);
            manager.remove(vote);
    }
}
