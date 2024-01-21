package com.example.manage.domain.delivery;

import com.example.manage.domain.as.As;
import com.example.manage.domain.as.AsUpdateParam;
import com.example.manage.domain.as.SpringDataJpaAsRepository;
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
public class DeliveryRepository {
    private final SpringDataJpaDeliRepository repository;

    public Delivery save(Delivery as) {
        repository.save(as);
        return as;
    }
    public List<Delivery> findAll(){
        List<Delivery> all = repository.findAll();
        return all;
    }
    public List<Delivery> findBySerial(String serial){
        return repository.findBySerialLike(serial);
    }
    public List<Delivery> company(String param){
        List<Delivery> result = new ArrayList<>();
        List<Delivery> all = repository.findAll();
        for(Delivery i : all) {
            if (i.getCompany().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }

    public List<Delivery> item(String param){
        List<Delivery> items = repository.findByItemLike(param);
        return items;
    }

    public List<Delivery> input(String param){
        List<Delivery> result = new ArrayList<>();
        List<Delivery> all = repository.findAll();
        for(Delivery i : all) {
            if (i.getInput().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }
    public List<Delivery> output(String param){
        List<Delivery> result = new ArrayList<>();
        List<Delivery> all = repository.findAll();
        for(Delivery i : all) {
            if (i.getOutput().contains(param)) {
                log.info("결과 : {}", i.getCompany());
                result.add(i);
            }
        }
        return result;
    }
    public List<Delivery> status(String param){
        List<Delivery> result = new ArrayList<>();
        List<Delivery> all = repository.findAll();
        for(Delivery i : all) {
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

    public Delivery findById(Long id) {
        return repository.findById(id).get();
    }

    public void update(Long id, DeliUpdateParam param){
        Optional<Delivery> as = repository.findById(id);
        String date = param.getDate();
        log.info("date = {}",date);
        log.info("param = {}",param.getStatus());
        log.info("as.get = {}",as.get().getStatus());

        LocalDate modified = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String result = modified.format(DateTimeFormatter.ofPattern("yyMMdd"));


        as.get().setStatus(as.get().getStatus()+"/"+result+","+param.getStatus());
    }
    public void delay(Long id, DeliUpdateParam param){
        Optional<Delivery> delivery = repository.findById(id);
        delivery.get().setDelay(param.getDelay());
    }
}
