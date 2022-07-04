package com.spr.votingsystem.controller;

import com.spr.votingsystem.interfaces.PartyLocal;
import com.spr.votingsystem.model.Candidate;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Party;
import com.spr.votingsystem.utilities.HelperFunctions;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Stateless
public class PartyController implements PartyLocal {

    @PersistenceContext
    private EntityManager manager;

    public Party getPartyById(Integer id) {
        return manager.find(Party.class, id);
    }

    public Party addParty(String party_name, String party_description) {
        Party party = new Party();
        party.setName(party_name);
        party.setDescription(party_description);
        party.setCode(HelperFunctions.generateRandomString(5));
        manager.persist(party);
        return party;
    }

    public Party addCandidate(Integer partyID, Integer candidateID) {
        Party party = manager.find(Party.class, partyID);
        Set<Candidate> candidates = party.getCandidates();
        Candidate retrievedCand = manager.find(Candidate.class, candidateID);
//            candidates.add(candidate);
        candidates.add(retrievedCand);
        party.setCandidates(candidates);
        return manager.merge(party);
    }

    public Party addElection(Integer partyID, Integer electionID) {
        Party party = manager.find(Party.class, partyID);
        Set<Election> elections = party.getElections();
        Election retrievedElection = manager.find(Election.class, electionID);
        elections.add(retrievedElection);
        party.setElections(elections);
        return manager.merge(party);
    }

    public Party deleteCandidate(Integer partyID, Integer candidateID) {
        Party party = manager.find(Party.class, partyID);
        Set<Candidate> candidates = party.getCandidates();
        candidates.removeIf(candidate -> candidate.getId() == candidateID);
        party.setCandidates(candidates);
        return manager.merge(party);
    }

    public Party deleteElection(Integer partyID, Integer electionID) {
        Party party = manager.find(Party.class, partyID);
        Set<Election> elections = party.getElections();
        elections.removeIf(candidate -> candidate.getId() == electionID);
        party.setElections(elections);
        return manager.merge(party);
    }

    public List<Party> listParties() {
        return manager.createQuery("select p from Party p", Party.class).getResultList();
    }

    public List<Party> listPartiesBy(String field, Object value) {
        return manager.createQuery("select p from Party p." + field + " = :value1", Party.class)
                .setParameter("value1", value)
                .getResultList();
    }

    public Party updateParty(Integer partyID, String party_name, String party_description) {
        Party party = manager.find(Party.class, partyID);
        party.setName(party_name);
        party.setDescription(party_description);
        return manager.merge(party);
    }

    public void deleteParty(Integer partyID) {
        Party party = manager.find(Party.class, partyID);
        manager.remove(party);
    }
}
