package com.example.manage.domain.knDrone;

import com.example.manage.domain.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaDroneInfoRepository  extends JpaRepository<DroneInfo,String> {
    DroneInfo findByCompanyLike(String company);
    List<DroneInfo> findBySerialLike(String serial);
    List<DroneInfo> findByDroneLike(String drone);
}
