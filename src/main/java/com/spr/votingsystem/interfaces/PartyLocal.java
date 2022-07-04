package com.spr.votingsystem.interfaces;

import com.spr.votingsystem.model.Party;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface PartyLocal {

    public Party getPartyById(Integer id);

    public Party addParty(String party_name, String party_description);

    public Party addCandidate(Integer partyID, Integer candidateID);

    public Party addElection(Integer partyID, Integer electionID);

    public Party deleteCandidate(Integer partyID, Integer candidateID);

    public Party deleteElection(Integer partyID, Integer electionID);

    public List<Party> listParties();

    public List<Party> listPartiesBy(String field, Object value);

    public Party updateParty(Integer partyID, String party_name, String party_description);

    public void deleteParty(Integer partyID);

}
