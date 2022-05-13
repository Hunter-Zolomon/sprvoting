package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "candidate_id")
    private int id;

    @Column(name = "candidate_first_name", nullable = false)
    private String first_name;

    @Column(name = "candidate_last_name", nullable = false)
    private String last_name;

    @Column(name = "candidate_qualifications", nullable = false)
    private String qualifications;

    @OneToOne
    private Party party;

    @OneToMany
    private List<Election> elections;

    public Candidate() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public List<Election> getElections() {
        return elections;
    }

    public void setElections(List<Election> elections) {
        this.elections = elections;
    }
}
