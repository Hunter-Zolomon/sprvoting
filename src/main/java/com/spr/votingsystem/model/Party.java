package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "party_id")
    private int id;

    @Column(name = "party_code", nullable = false, unique = true)
    private String code;

    @Column(name = "party_name", nullable = false, unique = true)
    private String name;

    @Column(name = "party_description", nullable = false)
    private String description;

    @OneToMany(targetEntity = Candidate.class, cascade = CascadeType.ALL, mappedBy = "party")
//    @JoinTable(
//            name = "parties_candidates",
//            joinColumns = { @JoinColumn(name = "party_id") },
//            inverseJoinColumns = { @JoinColumn(name = "candidate_id") },
//            uniqueConstraints = { @UniqueConstraint(columnNames = { "party_id", "candidate_id" })})
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(targetEntity = Election.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
