package com.example.manage.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByCompanyLike(String company);
    List<Item> findByCustomerLike(String customer); //게시물 이름으로 검색
    List<Item> findByTypeLike(String type);
    List<Item> findByItemNameLike(String itemName);
    List<Item> findByDetailLike(String detail);
    List<Item> findByManagerLike(String id);
    List<Item> findBySerialLike(String serial);
    List<Item> findByCompleteTrue();
    List<Item> findByCompleteFalse();

    @Query("select p from Item p where p.customer like :customer and p.type like :type and p.itemName like :itemName and p.detail like :detail")
    List<Item> findItems(@Param("customer")String customer, @Param("type")String type, @Param("itemName")String itemName, @Param("detail")String detail);

}
