package com.example.manage.domain.item;

import com.example.manage.web.item.form.MemoUpdateForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

@Transactional
@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final SpringDataJpaItemRepository repository;


    public Item save(Item item) {
        repository.save(item);
        return item;
    }

    public Item findById(Long id) {
        return repository.findById(id).get();
    }
    public List<Item> findByManagerId(String id){
        return repository.findByManagerLike(id);
    }

    public List<Item> findBySerial(String serial){
        return repository.findBySerialLike(serial);
    }
    public List<Item> findAll(ItemSearchCond cond) {
        String customer = cond.getCustomer();
        String type = cond.getType();
        String itemName = cond.getItemName();
        String detail = cond.getDetail();

        if(StringUtils.hasText(customer) && StringUtils.hasText(type) && StringUtils.hasText(itemName) && StringUtils.hasText(detail)){
            return repository.findItems("%"+customer+"%",type,itemName,"%"+detail+"%");
        }else if(StringUtils.hasText(customer)){
            return repository.findByCustomerLike("%"+customer+"%");
        }else if(StringUtils.hasText(type)){
            return repository.findByTypeLike(type);
        }
        else if(StringUtils.hasText(itemName)){
            return repository.findByItemNameLike(itemName);
        }
        else if(StringUtils.hasText(detail)){
            return repository.findByDetailLike("%"+detail+"%");
        } else {
            return repository.findAll();
        }
    }



    public void update(Long itemId, Item updateParam) {
        Item findPost = repository.findById(itemId).orElseThrow();
        findPost.setDate(updateParam.getDate());
        findPost.setCompany(updateParam.getCompany());
        findPost.setCustomer(updateParam.getCustomer());
        findPost.setPhone(updateParam.getPhone());
        findPost.setType(updateParam.getType());
        findPost.setItemName(updateParam.getItemName());
        findPost.setDetail(updateParam.getDetail());
        findPost.setSolution(updateParam.getSolution());
        findPost.setComplete(updateParam.getComplete());
        findPost.setManager(updateParam.getManager());
        findPost.setMemo(updateParam.getMemo());
    }

    public void completeUpdate(Long itemId){
        Item findPost = repository.findById(itemId).orElseThrow();
        findPost.setComplete(true);
    }
    public void addSerial(Long itemId,String serial){
        Item findPost = repository.findById(itemId).orElseThrow();
        findPost.setSerial(serial);
    }
    public void memoUpdate(Long itemId, MemoUpdateForm form){
        Item findPost = repository.findById(itemId).orElseThrow();
        if(findPost.getMemo().equals("")){
            findPost.setMemo(form.getWriter()+"{,}"+form.getContent());
        }else {
            findPost.setMemo(findPost.getMemo()+"{/}"+form.getWriter()+"{,}"+form.getContent());
        }

    }
    public void setMemo(Long itemId, String memo){
        Item item = repository.findById(itemId).orElseThrow();
        item.setMemo(memo);
    }
    public void delete(Long itemId){
        Optional<Item> item = repository.findById(itemId);
        repository.delete(item.get());
    }

}
