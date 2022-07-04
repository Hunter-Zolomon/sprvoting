package com.spr.votingsystem.interfaces;

import com.spr.votingsystem.model.Vote;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface VoteLocal {

    public Vote getVoteById(Integer id);

    public Vote addVote(Integer candidateID);

    public List<Vote> listVotes();

    public Vote incrementVote(Integer voteID);

    public void deleteVote(Integer voteID);

}
