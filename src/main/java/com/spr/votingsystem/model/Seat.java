package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    private int id;

    @Column(name = "seat_no_contesters")
    private int number_contesters;

    @ManyToMany(targetEntity = Candidate.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinTable(
//            name = "seats_candidates",
//            joinColumns = { @JoinColumn(name = "seat_id") },
//            inverseJoinColumns = { @JoinColumn(name = "candidate_id") },
//            uniqueConstraints = { @UniqueConstraint(columnNames = { "seat_id", "candidate_id"}) })
    private Set<Candidate> contesters = new HashSet<>();

    @OneToMany(targetEntity = Vote.class, cascade = CascadeType.ALL, mappedBy = "seat")
    private Set<Vote> votes = new HashSet<>();

    @ManyToOne(targetEntity = Election.class)
    private Election election;

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

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public void addVoteChild(Vote vote) {
        this.votes.add(vote);
        vote.setSeat(this);
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }
}
