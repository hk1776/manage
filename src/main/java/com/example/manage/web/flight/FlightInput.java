package com.example.manage.web.flight;

import com.example.manage.domain.flight.Flight;
import lombok.Data;

import java.util.List;

@Data
public class FlightInput {
    private List<Flight> flightList;
    private List<Integer>  delete;

    public FlightInput(List<Flight> flightList, List<Integer> delete) {
        this.flightList = flightList;
        this.delete = delete;
    }
}
