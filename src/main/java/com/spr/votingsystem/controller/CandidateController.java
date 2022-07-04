package com.spr.votingsystem.controller;

import com.spr.votingsystem.interfaces.CandidateLocal;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Party;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Stateless
public class CandidateController implements CandidateLocal {

    @PersistenceContext
    private EntityManager manager;

    public Candidate getCandidateById(Integer id) {
        try {
            return manager.find(Candidate.class, id);
        } catch (EJBException ex) {
            return null;
        }
    }

    public Candidate addCandidate(String fname, String lname, String quals, Integer partyID) {
        Candidate candidate = new Candidate();
        candidate.setFirst_name(fname);
        candidate.setLast_name(lname);
        candidate.setQualifications(quals);
        Party retrievedParty = manager.find(Party.class, partyID);
        candidate.setParty(retrievedParty);
//            manager.persist(candidate);
        return manager.merge(candidate);
    }

    public Candidate addCandidateToElection(Integer candidateID, Integer electionID) {
        Candidate candidate = manager.find(Candidate.class, candidateID);
        Set<Election> electionSet = candidate.getElections();
        Election foundElection = manager.find(Election.class, electionID);
        electionSet.add(foundElection);
        candidate.setElections(electionSet);
//            manager.persist(candidate);
        return manager.merge(candidate);
    }

    public List<Candidate> listCandidates() {
        return manager.createQuery("select c from Candidate c", Candidate.class)
                .getResultList();
    }

    public List<Candidate> listCandidatesBy(String field, Object value) {
        return manager.createQuery("select c from Candidate c." + field + " = :value1", Candidate.class)
                .setParameter("value1", value)
                .getResultList();
    }

    public Candidate updateCandidate(Integer candidateID, String fname, String lname, String quals, Integer partyID) {
        Candidate candidate = manager.find(Candidate.class, candidateID);
        candidate.setFirst_name(fname);
        candidate.setLast_name(lname);
        candidate.setQualifications(quals);
        Party retrievedParty = manager.find(Party.class, partyID);
        candidate.setParty(retrievedParty);
        return manager.merge(candidate);
    }

    public boolean deleteCandidate(Integer candidateID) {
        boolean success = true;

        try {
            Candidate candidate = manager.find(Candidate.class, candidateID);
            manager.remove(candidate);
        } catch (EJBException ex) {
            success = false;
        }

        return success;
    }
}
