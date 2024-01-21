package com.example.manage.domain.flight;

import com.example.manage.domain.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaFlightRepository extends JpaRepository<Flight,Long> {
}
