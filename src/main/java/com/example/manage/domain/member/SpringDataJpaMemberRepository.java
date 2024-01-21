package com.example.manage.domain.member;

import com.example.manage.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>{
    List<Member> findByRankLike(String rank);
    @Query("SELECT u FROM Member u WHERE u.rank <> :specificValue")
    List<Member> findAllExceptSpecificValue(@Param("specificValue") String specificValue);
}
