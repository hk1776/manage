package com.example.manage.domain.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class RoleRepository {
    private final SpringDataJpaRoleRepository repository;


    public Role findById(String id){
        Optional<Role> role = repository.findById(id);
        return role.get();
    }

    public void update(Role update){
        Optional<Role> role = repository.findById("RV");
        role.get().setAs(update.getAs());
        role.get().setAsS(update.getAsS());
        role.get().setScheduleM(update.getScheduleM());
        role.get().setScheduleS(update.getScheduleS());
        role.get().setProduceM(update.getProduceM());
        role.get().setProduceS(update.getProduceS());
        role.get().setEduM(update.getEduM());
        role.get().setEduS(update.getEduS());
        role.get().setDocM(update.getDocM());
        role.get().setDocS(update.getDocS());


    }


}
