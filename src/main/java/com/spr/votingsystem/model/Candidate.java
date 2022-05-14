package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(targetEntity = Seat.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "candidates_seats",
            joinColumns = { @JoinColumn(name = "candidate_id") },
            inverseJoinColumns = { @JoinColumn(name = "seat_id") },
            uniqueConstraints = { @UniqueConstraint(columnNames = { "candidate_id", "seat_id"}) })
    private Set<Seat> seats = new HashSet<>();

    @ManyToMany(targetEntity = Election.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "candidates_elections",
            joinColumns = { @JoinColumn(name = "candidate_id") },
            inverseJoinColumns = { @JoinColumn(name = "election_id") },
            uniqueConstraints = { @UniqueConstraint(columnNames = { "candidate_id", "election_id"}) })
    private Set<Election> elections = new HashSet<>();

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

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Set<Election> getElections() {
        return elections;
    }

    public void setElections(Set<Election> elections) {
        this.elections = elections;
    }
}
