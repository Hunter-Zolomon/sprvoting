package com.spr.votingsystem.utilities;

import com.spr.votingsystem.controller.ElectionController;
import com.spr.votingsystem.interfaces.ElectionLocal;
import com.spr.votingsystem.model.Election;
import com.spr.votingsystem.model.Seat;
import com.spr.votingsystem.model.User;
import com.spr.votingsystem.model.Vote;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.util.*;
import java.util.stream.Collectors;

public class ReportGeneration {

//    private final ElectionController electcont;
//    @EJB
//    private ElectionLocal electcont;
    private final List<User> all_election_users;
    private final Set<Seat> all_election_seats;

    public ReportGeneration(Election electionObj) {
//        electcont = new ElectionController();
//        Election retrievedElection = electcont.getElectionById(election_id);
        this.all_election_seats = electionObj.getSeats();
        this.all_election_users = new ArrayList<>();
        for (Seat seat : this.all_election_seats) {
            Set<Vote> temp_list = seat.getVotes();
            for (Vote vote : temp_list) {
                this.all_election_users.addAll(vote.getUsers());
            }
        }
    }

    public List<Integer> generateAgeAnalysis() {
        List<Integer> age_list = new ArrayList<>(Collections.nCopies(8, 0));

        for (User user : this.all_election_users) {
            int age = user.getAge();
            if (age >= 18 && age <= 28) {
                age_list.set(0, age_list.get(0) + 1);
            } else if (age >= 29 && age <= 39) {
                age_list.set(1, age_list.get(1) + 1);
            } else if (age >= 40 && age <= 50) {
                age_list.set(2, age_list.get(2) + 1);
            } else if (age >= 51 && age <= 61) {
                age_list.set(3, age_list.get(3) + 1);
            } else if (age >= 62 && age <= 72) {
                age_list.set(4, age_list.get(4) + 1);
            } else if (age >= 73 && age <= 83) {
                age_list.set(5, age_list.get(5) + 1);
            } else if (age >= 84 && age <= 94) {
                age_list.set(6, age_list.get(6) + 1);
            } else if (age >= 95 && age <= 99) {
                age_list.set(7, age_list.get(7) + 1);
            }
        }

        return age_list;
    }

    public List<Integer> generateIncomeAnalysis() {
        List<Integer> income_list = new ArrayList<>(Collections.nCopies(13, 0));

        for (User user : this.all_election_users) {
            double income = user.getIncome();
            if (income < 10000.0) {
                income_list.set(0, income_list.get(0) + 1);
            } else if (income >= 10000.0 && income < 20000.0) {
                income_list.set(1, income_list.get(1) + 1);
            } else if (income >= 20000.0 && income < 30000.0) {
                income_list.set(2, income_list.get(2) + 1);
            } else if (income >= 30000.0 && income < 40000.0) {
                income_list.set(3, income_list.get(3) + 1);
            } else if (income >= 40000.0 && income < 50000.0) {
                income_list.set(4, income_list.get(4) + 1);
            } else if (income >= 50000.0 && income < 60000.0) {
                income_list.set(5, income_list.get(5) + 1);
            } else if (income >= 60000.0 && income < 70000.0) {
                income_list.set(6, income_list.get(6) + 1);
            } else if (income >= 70000.0 && income < 80000.0) {
                income_list.set(7, income_list.get(7) + 1);
            } else if (income >= 80000.0 && income < 90000.0) {
                income_list.set(8, income_list.get(8) + 1);
            } else if (income >= 90000.0 && income < 100000.0) {
                income_list.set(9, income_list.get(9) + 1);
            } else if (income >= 100000.0 && income < 110000.0) {
                income_list.set(10, income_list.get(10) + 1);
            } else if (income >= 110000.0 && income < 120000.0) {
                income_list.set(11, income_list.get(11) + 1);
            } else if (income >= 120000.0) {
                income_list.set(12, income_list.get(12) + 1);
            }
        }

        return income_list;
    }

    public List<Integer> generateGenderAnalysis() {
        List<Integer> gender_list = new ArrayList<>(Collections.nCopies(2, 0));

        for (User user : this.all_election_users) {
            if (user.getGender().equals("Male")) {
                gender_list.set(0, gender_list.get(0) + 1);
            } else if (user.getGender().equals("Female")) {
                gender_list.set(1, gender_list.get(1) + 1);
            }
        }

        return gender_list;
    }

    public Map<String, Long> generateRaceAnalysis() {
        return this.all_election_users
                .stream()
                .collect(Collectors.groupingBy(User::getRace, Collectors.counting()));
    }

    public Map<String, Long> generateReligionAnalysis() {
        return this.all_election_users
                .stream()
                .collect(Collectors.groupingBy(User::getReligion, Collectors.counting()));
    }

    public Map<String, Map<String, Long>> generateEducationAnalysis() {
        Map<String, Long> male = this.all_election_users
                .stream()
                .filter(entry -> entry.getGender().equals("Male"))
                .collect(Collectors.groupingBy(User::getEducation, Collectors.counting()));
        Map<String, Long> female = this.all_election_users
                .stream()
                .filter(entry -> entry.getGender().equals("Female"))
                .collect(Collectors.groupingBy(User::getEducation, Collectors.counting()));
        return new HashMap<String, Map<String, Long>>() {{
            put("Male", male);
            put("Female", female);
        }};
    }

    public Map<Integer, Map<String, Long>> generateSeatVoteAnalysis() {
//        Seat foundSeat = this.all_election_seats
//                            .stream()
//                            .filter(seat -> seat.getId() == seatID)
//                            .findFirst().get();
        Map<Integer, Map<String, Long>> resultMap = new HashMap<>();
        for (Seat seat : this.all_election_seats) {
            Map<String, Long> temp_map = new HashMap<>();
            for (Vote vote: seat.getVotes()) {
                temp_map.put(vote.getCandidate().getFirst_name(), (long) vote.getVoteCount());
            }
            resultMap.put(seat.getId(), temp_map);
        }
        return resultMap;
    }
}
