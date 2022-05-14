package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "party_id")
    private int id;

    @Column(name = "party_code", nullable = false)
    private String code;

    @Column(name = "party_name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Candidate.class, cascade = CascadeType.ALL)
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(targetEntity = Election.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "parties_elections",
            joinColumns = { @JoinColumn(name = "party_id") },
            inverseJoinColumns = { @JoinColumn(name = "election_id") },
            uniqueConstraints = { @UniqueConstraint(columnNames = { "party_id", "election_id"}) })
    private Set<Election> elections = new HashSet<>();

    public Party() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<Election> getElections() {
        return elections;
    }

    public void setElections(Set<Election> elections) {
        this.elections = elections;
    }
}
