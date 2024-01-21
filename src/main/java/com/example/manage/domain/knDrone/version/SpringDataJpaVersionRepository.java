package com.example.manage.domain.knDrone.version;

import com.example.manage.domain.knDrone.DroneInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaVersionRepository extends JpaRepository<Version,String> {
    Version findByItem(String id);
}
