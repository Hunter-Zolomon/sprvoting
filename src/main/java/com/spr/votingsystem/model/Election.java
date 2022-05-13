package com.spr.votingsystem.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "election_id")
    private int id;

    @Column(name = "election_name", nullable = false)
    private String name;

    @Column(name = "election_no_seats", nullable = false)
    private int number_seats;

    @Column(name = "election_date", nullable = false)
    private Date date;

    @OneToMany
    private List<Seat> seats;

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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
