package com.example.manage.domain.flight;

import com.example.manage.domain.as.As;
import com.example.manage.domain.as.AsUpdateParam;
import com.example.manage.domain.as.SpringDataJpaAsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@Slf4j
@RequiredArgsConstructor
public class FlightRepository {
    private final SpringDataJpaFlightRepository repository;


    public Flight save(Flight flight) {
        repository.save(flight);
        return flight;
    }
    public List<Flight> findAll(){
        List<Flight> all = repository.findAll();
        return all;
    }

    public void update(Long id, AsUpdateParam param){
        Optional<Flight> flight = repository.findById(id);
        String date = param.getDate();
        LocalDate modified = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String result = modified.format(DateTimeFormatter.ofPattern("yyMMdd"));
        flight.get().setStart(result);
    }

    public Flight findById(Long id){
        return repository.findById(id).get();
    }
    public void delete(long id){
        Optional<Flight> flight = repository.findById(id);
        repository.delete(flight.get());
    }
}
