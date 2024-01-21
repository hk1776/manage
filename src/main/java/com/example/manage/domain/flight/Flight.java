package com.example.manage.domain.flight;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "FLIGHT")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String people;
    private String type;
    private String location;
    private String item;
    private String start;
    @Column(name = "END_TIME")
    private String end;

    public Flight() {
    }

    public Flight(String people, String type, String location, String item, String start, String end) {
        this.people = people;
        this.type = type;
        this.location = location;
        this.item = item;
        this.start = start;
        this.end = end;
    }
}
