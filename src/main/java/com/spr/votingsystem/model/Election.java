package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "election_id")
    private int id;

    @Column(name = "election_name", nullable = false, unique = true)
    private String name;

    @Column(name = "election_no_seats", nullable = false)
    private int number_seats;

    @Column(name = "election_date", nullable = false)
    private Date date;

    @Column(name = "election_completed", nullable = false)
    private boolean completed;

    @OneToMany(targetEntity = Seat.class, cascade = CascadeType.ALL, mappedBy = "election")
    private Set<Seat> seats;

    @ManyToMany(targetEntity = Candidate.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(targetEntity = Party.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "elections")
//    @JoinTable(
//            name = "elections_parties",
//            joinColumns = { @JoinColumn(name = "election_id") },
//            inverseJoinColumns = { @JoinColumn(name = "party_id") },
//            uniqueConstraints = { @UniqueConstraint(columnNames = { "election_id", "party_id"}) })
    private Set<Party> parties = new HashSet<>();

    public Election() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_seats() {
        return number_seats;
    }

    public void setNumber_seats(int number_seats) {
        this.number_seats = number_seats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public void addSeatChild(Seat seat) {
        this.seats.add(seat);
        seat.setElection(this);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<Party> getParties() {
        return parties;
    }

    public void setParties(Set<Party> parties) {
        this.parties = parties;
    }
}
