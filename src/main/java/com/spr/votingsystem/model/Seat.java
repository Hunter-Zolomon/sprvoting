package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    private int id;

    @Column(name = "seat_no_contesters")
    private int number_contesters;

    @ManyToMany(targetEntity = Candidate.class, cascade = CascadeType.ALL) //Todo ManyToMany
    @JoinTable(
            name = "seats_candidates",
            joinColumns = { @JoinColumn(name = "seat_id") },
            inverseJoinColumns = { @JoinColumn(name = "candidate_id") },
            uniqueConstraints = { @UniqueConstraint(columnNames = { "seat_id", "candidate_id"}) })
    private Set<Candidate> contesters = new HashSet<>();

    public Seat() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_contesters() {
        return number_contesters;
    }

    public void setNumber_contesters(int number_contesters) {
        this.number_contesters = number_contesters;
    }

    public Set<Candidate> getContesters() {
        return contesters;
    }

    public void setContesters(Set<Candidate> contesters) {
        this.contesters = contesters;
    }
}
