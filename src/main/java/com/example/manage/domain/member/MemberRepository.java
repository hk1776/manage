package com.example.manage.domain.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepository {
    private final SpringDataJpaMemberRepository repository;

    // private static Map<Long, Member> store = new HashMap<>(); //static 사용

    public Member save(Member member) {
        log.info("save: member={}", member);
        repository.save(member);
        return member;
    }

    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(loginId))
                return Optional.of(m);
        }
        return Optional.empty();
    }

    public List<Member> findAll() {
        return repository.findAll();
    }
    public List<Member> findGuest(String rank) {
        return repository.findByRankLike(rank);
    }
    public void upgrade(long id){
        Optional<Member> member = findById(id);
        member.get().setRank("normal");
    }

    public void upDateA(Long id ,Member memberP){
        Member member = repository.findById(id).get();
        member.setRank(memberP.getRank());
        member.setPosition(memberP.getPosition());
        member.setCall(memberP.getCall());
    }
    public List<Member> managers(){
        return repository.findAllExceptSpecificValue("guest");
    }
    public Member delete(Member member) {
        repository.delete(member);
        return null;

    }


}
