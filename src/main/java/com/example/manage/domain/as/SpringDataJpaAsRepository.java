package com.example.manage.domain.as;

import com.example.manage.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaAsRepository extends JpaRepository<As,Long> {
    List<As> findByItemLike(String itemName);
    List<As> findBySerialLike(String serial);
}
