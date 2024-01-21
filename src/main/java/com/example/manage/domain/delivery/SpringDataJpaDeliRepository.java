package com.example.manage.domain.delivery;

import com.example.manage.domain.as.As;
import com.example.manage.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaDeliRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findByItemLike(String itemName);
    List<Delivery> findBySerialLike(String serial);
}
