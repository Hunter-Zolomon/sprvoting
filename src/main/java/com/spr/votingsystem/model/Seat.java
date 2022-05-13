package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    private int id;

    @Column(name = "seat_no_contesters")
    private int number_contesters;

    @OneToMany
    private List<Candidate> contesters;

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

    public List<Candidate> getContesters() {
        return contesters;
    }

    public void setContesters(List<Candidate> contesters) {
        this.contesters = contesters;
    }
}
