package com.example.manage.web.member;

import com.example.manage.domain.item.Item;
import com.example.manage.domain.item.ItemRepository;
import com.example.manage.domain.member.Member;
import com.example.manage.domain.member.MemberRepository;
import com.example.manage.domain.role.Role;
import com.example.manage.domain.role.RoleInput;
import com.example.manage.domain.role.RoleRepository;
import com.example.manage.web.argumentresolver.Login;
import com.example.manage.web.item.form.ItemSaveForm;
import com.example.manage.web.item.form.ItemUpdateForm;
import jakarta.validation.Valid;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController{

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final RoleRepository roleRepository;
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }
        member.setRank("guest");
        memberRepository.save(member);
        return "redirect:/";
    }
    @GetMapping("/info")
    public String memberInfo(@Login Member loginMember, Model model,@RequestParam(defaultValue = "0") int page) {
        if (loginMember == null) {
            return "home";
        }
        List<Item> proceeding = itemRepository.findByManagerId(String.valueOf(loginMember.getId()));

        Collections.reverse(proceeding);
        log.info("size={}",proceeding.size());

        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), proceeding.size());
        Page<Item> pages = new PageImpl<>(proceeding.subList(start, end), pageRequest, proceeding.size());


        model.addAttribute("items",pages);
        model.addAttribute("member",loginMember);
        model.addAttribute("itemLen",proceeding.size());
        model.addAttribute("maxPage", 10);
        model.addAttribute("thisP",page+1);
        if(loginMember.getRank().equals("guest")){
            model.addAttribute("rank","게스트");
        } else if (loginMember.getRank().equals("admin")) {
            model.addAttribute("rank","관리자");
        }else {
            model.addAttribute("rank","일반");
        }

        return "members/myPage";
    }
    @GetMapping("/admin")
    public String memberAdmin(@Login Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }
        List<Item> items = itemRepository.findByManagerId(loginMember.getName());
        List<Member> guest = memberRepository.findGuest("guest");
        List<Member> all = memberRepository.findAll();
        Role role = roleRepository.findById("RV");
        model.addAttribute("role",role);
        model.addAttribute("guest",guest);
        model.addAttribute("items",items);
        model.addAttribute("member",loginMember);
        model.addAttribute("itemLen",items.size());
        model.addAttribute("all",all);
        return "members/adminPage";
    }
    @PostMapping("/admin")
    public String roleUpdate(@Login Member loginMember, @Validated @ModelAttribute("role") Role role) {
        if (loginMember == null) {
            return "home";
        }
        roleRepository.update(role);

        return "redirect:/members/admin";
    }

    @GetMapping("/info/{memberId}")
    public String memberUpgrade(@Login Member loginMember, Model model,@PathVariable long memberId) {
        if (loginMember == null) {
            return "home";
        }
       memberRepository.upgrade(memberId);

        return "redirect:/members/info";
    }


    @GetMapping("/admin/{memberId}")
    public String memberUpdateA(Model model,@PathVariable long memberId) {
        Member member = memberRepository.findById(memberId).get();
        model.addAttribute("member", member);
        return "members/memberEditForm";
    }
    @PostMapping("/admin/{memberId}")
    public String memberUpdate(@PathVariable Long memberId, @Validated @ModelAttribute("member") MemberUpdateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/memberEditForm";
        }
        Member memberParam = new Member(form.getRank(),form.getPosition(),form.getCall());
        memberRepository.upDateA(memberId,memberParam);
        return "redirect:/members/admin";
    }
}
