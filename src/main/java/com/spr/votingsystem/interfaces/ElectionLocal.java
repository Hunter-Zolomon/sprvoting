package com.spr.votingsystem.interfaces;

import com.spr.votingsystem.model.Election;
import jakarta.ejb.Local;

import java.util.Date;
import java.util.List;

@Local
public interface ElectionLocal {

    public Election getElectionById(Integer id);

    public Election addElection(String name, Date date, List<Integer> seat_numbers);

    public Election addElection(String name, Date date, Integer num_seats);

    public Election addCandidateToElection(Integer candidateID, Integer electionID);

    public Election deleteCandidateFromElection(Integer candidateID, Integer electionID);

    public List<Election> listElections();

    public List<Election> listElectionsBy(String field, Object value);

    public Election endElection(Integer electionID);

    public void deleteElection(Integer electionID);

}
