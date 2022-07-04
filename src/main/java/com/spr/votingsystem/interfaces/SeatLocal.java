package com.spr.votingsystem.interfaces;

import com.spr.votingsystem.model.Seat;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface SeatLocal {

    public Seat addSeat(Integer number_contesters);

    public Seat addCandidate(Integer seatID, Integer candidateID);

    public Seat deleteCandidate(Integer seatID, Integer candidateID);

    public Seat getSeatById(Integer id);

    public List<Seat> listSeats();

    public Seat updateSeat(Integer seatID, Integer number_contesters);

    public void deleteSeat(Integer seatID);

}
