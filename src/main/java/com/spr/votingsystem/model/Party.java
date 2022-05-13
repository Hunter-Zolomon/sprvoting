package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany
    private List<Candidate> candidates;

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

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
