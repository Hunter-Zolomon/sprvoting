package com.spr.votingsystem.interfaces;

import com.spr.votingsystem.model.Candidate;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface CandidateLocal {

    public Candidate getCandidateById(Integer ID);

    public Candidate addCandidate(String fname, String lname, String quals, Integer partyID);

    public Candidate addCandidateToElection(Integer candidateID, Integer electionID);

    public List<Candidate> listCandidates();

    public List<Candidate> listCandidatesBy(String field, Object value);

    public Candidate updateCandidate(Integer candidateID, String fname, String lname, String quals, Integer partyID);

    public boolean deleteCandidate(Integer candidateID);

}
