package com.example.manage.web.knDrone;

import com.example.manage.domain.item.Item;
import com.example.manage.domain.knDrone.DroneSearchCond;
import com.example.manage.domain.knDrone.version.Version;
import com.example.manage.domain.knDrone.version.VersionRepository;
import com.example.manage.domain.knDrone.version.VersionUpdateParam;
import com.example.manage.domain.member.Member;
import com.example.manage.domain.member.MemberRepository;
import com.example.manage.web.argumentresolver.Login;
import com.example.manage.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/version")
@RequiredArgsConstructor
public class VersionController {
    private final MemberRepository memberRepository;
    private final VersionRepository repository;


    @GetMapping("/KnX")
    public String knx(@Login Member loginMember, Model model, @ModelAttribute("itemSearch") DroneSearchCond searchCond) {
        //로그인 여부 체크
        if (loginMember == null) {
            return "home";
        }
        Version knX = repository.findById("KnX");
        model.addAttribute("droneM","KnX");
        model.addAttribute("version",knX);
        model.addAttribute("member", loginMember);
        return "drone/version";
    }
    @GetMapping("/KnX2")
    public String knx2(@Login Member loginMember, Model model, @ModelAttribute("itemSearch") DroneSearchCond searchCond) {
        //로그인 여부 체크
        if (loginMember == null) {
            return "home";
        }
        Version knX2 = repository.findById("KnX2");
        model.addAttribute("droneM","KnX2");
        model.addAttribute("version",knX2);
        model.addAttribute("member", loginMember);
        return "drone/version";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable String itemId,@Login Member loginMember,Model model) {
        if (loginMember == null) {
            return "home";
        }
        Version version = repository.findById(itemId);
        VersionUpdateParam updateParam = new VersionUpdateParam();
        //model.addAttribute("droneM",version.getItem());
        model.addAttribute("versionUpdate",updateParam);
        model.addAttribute("version", version);
        model.addAttribute("member", loginMember);
        return "drone/versionEdit";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable String itemId, @Validated @ModelAttribute("item") VersionUpdateParam form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "drone/versionEdit";
        }
        repository.update(itemId,form);
        return "redirect:/version/{itemId}";
    }

}
