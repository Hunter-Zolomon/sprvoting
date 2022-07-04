package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "candidate_candidate_id", "seat_seat_id"}))
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vote_id")
    private int id;

    @Column(name = "vote_count")
    private int voteCount = 0;

    @ManyToOne(targetEntity = Candidate.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinTable(
//            name = "votes_candidates",
//            joinColumns = { @JoinColumn(name = "vote_id") },
//            inverseJoinColumns = { @JoinColumn(name = "candidate_id") },
//            uniqueConstraints = { @UniqueConstraint(columnNames = { "vote_id", "candidate_id"}) })
    private Candidate candidate;

    @ManyToMany(targetEntity = User.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "votes")
//    @JoinTable(
//            name = "votes_users",
//            joinColumns = { @JoinColumn(name = "vote_id") },
//            inverseJoinColumns = { @JoinColumn(name = "user_id") },
//            uniqueConstraints = { @UniqueConstraint(columnNames = { "vote_id", "user_id"}) })
    private Set<User> users = new HashSet<>();

    @ManyToOne(targetEntity = Seat.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Seat seat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
