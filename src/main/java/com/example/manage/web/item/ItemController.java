package com.example.manage.web.item;

import com.example.manage.domain.as.As;
import com.example.manage.domain.item.Item;
import com.example.manage.domain.item.ItemRepository;
import com.example.manage.domain.item.ItemSearchCond;
import com.example.manage.domain.member.Member;
import com.example.manage.domain.member.MemberRepository;
import com.example.manage.web.argumentresolver.Login;
import com.example.manage.web.item.form.InputDbForm;
import com.example.manage.web.item.form.ItemSaveForm;
import com.example.manage.web.item.form.ItemUpdateForm;
import com.example.manage.web.item.form.MemoUpdateForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @GetMapping
    public String items(@Login Member loginMember, Model model, @ModelAttribute("itemSearch")ItemSearchCond itemSearch,@RequestParam(defaultValue = "0") int page,HttpServletRequest request) {
        String currentUrl = request.getRequestURL().toString();


        //로그인 여부 체크
        if (loginMember == null) {
            return "home";
        }
        log.info("item={},type={},company={},stat={}",itemSearch.getItemName(),itemSearch.getType(),itemSearch.getCompany(),itemSearch.getStatus());

        List<Item> items = itemRepository.findAll(itemSearch);
        List<Item> proceeding = new ArrayList<>();
        if(itemSearch.getItemName()==null&&itemSearch.getType()==null&&itemSearch.getCompany()==null&&itemSearch.getStatus()==null||itemSearch.getItemName().equals(" ")&&itemSearch.getType().equals(" ")&&itemSearch.getCompany().equals("")&&itemSearch.getStatus().equals(" ")||itemSearch.getItemName().equals("")&&itemSearch.getType().equals("")&&itemSearch.getCompany().equals("")&&itemSearch.getStatus().equals("")){
            log.info("empty");
            proceeding = items;
        }else {
            if(!itemSearch.getCompany().equals("")){
                String com = itemSearch.getCompany();
                if(com.startsWith(" ")){
                    com = com.substring(1);
                }
                log.info("com : {}",com);
                for(Item i : items){
                    if(i.getCompany().contains(com)){
                        proceeding.add(i);
                    }

                }
            }
           else if(!itemSearch.getStatus().equals(" ")){
                if(itemSearch.getStatus().equals("o")){
                    for(Item i : items){
                        if(i.getComplete()==true){
                            log.info("item={}",i);
                            proceeding.add(i);
                        }
                    }
                }else if(itemSearch.getStatus().equals("x")){
                    for(Item i : items){
                        if(i.getComplete()==false){
                            log.info("itemF={}",i);
                            proceeding.add(i);
                        }
                    }
                }
            }
            else {
                log.info("else");
                proceeding = items;
            }

        }

        Collections.reverse(proceeding);
        log.info("size={}",proceeding.size());

        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), proceeding.size());
        Page<Item> pages = new PageImpl<>(proceeding.subList(start, end), pageRequest, proceeding.size());

        log.info("urlT = {}",currentUrl);

        model.addAttribute("items", pages);
        model.addAttribute("member", loginMember);
        model.addAttribute("maxPage", 10);
        model.addAttribute("urlT", currentUrl);
        model.addAttribute("thisP",page+1);
       // model.addAttribute("member",member);

        return "items/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model,@Login Member loginMember) {
        //로그인 여부 체크
        Item item = itemRepository.findById(itemId);
        if(isNumber(item.getManager())){
            Optional<Member> manager = memberRepository.findById(Long.parseLong(item.getManager()));
            item.setManager(manager.get().getName());
        }

        model.addAttribute("memo",new MemoUpdateForm());
        model.addAttribute("item", item);
        model.addAttribute("member",loginMember);
        return "items/item";
    }


    @PostMapping("/{itemId}")
    public String memo(@PathVariable long itemId,@Validated @ModelAttribute("memo") MemoUpdateForm form, BindingResult bindingResult,@Login Member loginMember) {
        //로그인 여부 체크
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/"+itemId;
        }
        form.setWriter(loginMember.getName());
        itemRepository.memoUpdate(itemId, form);
        return "redirect:/items/{itemId}";
    }
    @GetMapping("/{itemId}/memoDel")
    public String memoDel(@PathVariable long itemId,@Login Member loginMember) {
        //로그인 여부 체크

        Item item = itemRepository.findById(itemId);
        String memo = item.getMemo();
        int lastIndex = memo.lastIndexOf("{/}");
        if (lastIndex != -1) { // 특정 문자열이 존재하는 경우
            String result = memo.substring(0, lastIndex); // 특정 문자열까지의 문자열을 추출
            itemRepository.setMemo(itemId,result);

        } else {
            itemRepository.setMemo(itemId,"");
        }

        return "redirect:/items/{itemId}";
    }


    @GetMapping("/add")
    public String addForm(@Login Member loginMember,Model model) {
        //로그인 여부 체크
        model.addAttribute("item", new Item());
        model.addAttribute("member", loginMember);
        List<Member> managers = memberRepository.managers();
        ArrayList<Member> simples = new ArrayList<>();
        for(int i=0;i<managers.size();i++){
            Member member = managers.get(i);
            Member simple = new Member(member.getId(),member.getName());
            simples.add(simple);
        }
        model.addAttribute("simples",simples);
        return "items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes,@Login Member loginMember,Model model) {

        //특정 필드 예외가 아닌 전체 예외
    /*    if (form.getComplete() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }*/
        log.info("serial={}",form.getSerial());

        model.addAttribute("member", loginMember);
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/addForm";
        }
        LocalDate currentDate = LocalDate.now();

        //성공 로직
        Item item = new Item(String.valueOf(currentDate),form.getCompany(),form.getCustomer(),form.getPhone(),form.getType(),form.getItemName(),form.getDetail(),form.getSolution(),false,form.getManager(),form.getMemo(),form.getSerial());

        Item savedItem = itemRepository.save(item);


        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }
   /* @ResponseBody
    @PostMapping("/addDb")
    public void addItemDb(@RequestBody List<InputDbForm> forms) {
        log.info("배열 크기 :{}",forms.size());
        for(InputDbForm form : forms){
            //성공 로직
            log.info("from :{}",form.getCompany());
            Item item = new Item(Long.parseLong(form.getId()),form.getDate(),form.getCompany(),form.getCustomer(),form.getPhone(),form.getType(),form.getItemName(),form.getDetail(),form.getSolution(),form.getComplete(),form.getManager(),form.getMemo());
            itemRepository.save(item);
        }
    }*/


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model, @RequestParam("serial") String serial) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        List<Member> managers = memberRepository.managers();
        ArrayList<Member> simples = new ArrayList<>();
        for(int i=0;i<managers.size();i++){
            Member member = managers.get(i);
            Member simple = new Member(member.getId(),member.getName());
            simples.add(simple);
        }
        model.addAttribute("simples",simples);
        model.addAttribute("serial",serial);
        return "items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/editForm";
        }
        Item item = itemRepository.findById(itemId);

        Item itemParam = new Item(form.getDate(),form.getCompany(),form.getCustomer(),form.getPhone(),form.getType(),form.getItemName(),form.getDetail(),form.getSolution(),form.getComplete(),form.getManager(),item.getMemo(),form.getSerial());


        itemRepository.update(itemId, itemParam);
        return "redirect:/items/{itemId}";
    }
    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {
        log.info("deleteIn");
        itemRepository.delete(itemId);
        return "redirect:/items";
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
