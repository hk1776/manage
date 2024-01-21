package com.example.manage.domain.as;


import com.example.manage.domain.item.Item;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@Slf4j
@RequiredArgsConstructor
public class AsRepository {
    private final SpringDataJpaAsRepository repository;

    public As save(As as) {
        repository.save(as);
        return as;
    }
    public List<As> findAll(){
        List<As> all = repository.findAll();
        return all;
    }

    public List<As> company(String param){
        List<As> result = new ArrayList<>();
        List<As> all = repository.findAll();
        for(As i : all) {
            if (i.getCompany().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }

    public List<As> item(String param){
        List<As> items = repository.findByItemLike(param);
        return items;
    }

    public List<As> input(String param){
        List<As> result = new ArrayList<>();
        List<As> all = repository.findAll();
        for(As i : all) {
            if (i.getInput().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }
    public List<As> output(String param){
        List<As> result = new ArrayList<>();
        List<As> all = repository.findAll();
        for(As i : all) {
            if (i.getOutput().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }
    public List<As> status(String param){
        List<As> result = new ArrayList<>();
        List<As> all = repository.findAll();
        for(As i : all) {
            if(param.equals("0")){
                if(i.getStatus().equals("1")){
                    result.add(i);
                }
            }else {
                if(i.getStatus().substring(i.getStatus().length()-1).equals(param)&&i.getStatus().length()>2){
                    result.add(i);
                }
            }
        }
        return result;
    }

    public As findById(Long id) {
        return repository.findById(id).get();
    }

    public void update(Long id,AsUpdateParam param){
        Optional<As> as = repository.findById(id);
        String date = param.getDate();
        log.info("date = {}",date);
        log.info("param = {}",param.getStatus());
        log.info("as.get = {}",as.get().getStatus());

        LocalDate modified = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String result = modified.format(DateTimeFormatter.ofPattern("yyMMdd"));


        as.get().setStatus(as.get().getStatus()+"/"+result+","+param.getStatus());
    }
    public void delay(Long id, AsUpdateParam param){
        Optional<As> as = repository.findById(id);
        as.get().setDelay(param.getDelay());
    }
    public List<As> findBySerial(String serial){
        return repository.findBySerialLike(serial);
    }
}
