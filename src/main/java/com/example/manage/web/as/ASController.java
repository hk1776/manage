package com.example.manage.web.as;

import com.example.manage.domain.as.As;
import com.example.manage.domain.as.AsRepository;
import com.example.manage.domain.as.AsUpdateParam;
import com.example.manage.domain.item.Item;
import com.example.manage.domain.item.ItemRepository;
import com.example.manage.domain.item.ItemSearchCond;
import com.example.manage.domain.member.Member;
import com.example.manage.domain.member.MemberRepository;
import com.example.manage.domain.role.Role;
import com.example.manage.domain.role.RoleRepository;
import com.example.manage.web.argumentresolver.Login;
import com.example.manage.web.item.form.InputDbForm;
import com.example.manage.web.item.form.ItemSaveForm;
import com.example.manage.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/as")
@RequiredArgsConstructor
public class ASController {

    private final AsRepository repository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final RoleRepository roleRepository;
    @GetMapping
    public String as(@Login Member loginMember, Model model, @ModelAttribute("itemSearch")ItemSearchCond itemSearch,@RequestParam(defaultValue = "0") int page) {
        //로그인 여부 체크
        if (loginMember == null) {
            return "home";
        }
        List<As> items;

        log.info("name={},type={}",itemSearch.getSelect(),itemSearch.getItemName());
                if(itemSearch.getSelect()==null){
                    items = repository.findAll();
                }else {
                    log.info("검색 시작");
                    if(itemSearch.getSelect().equals("1")){
                        log.info("회사검색");
                        items = repository.company(itemSearch.getCompany());
                    } else if (itemSearch.getSelect().equals("2")) {
                        log.info("제품검색");
                        if(itemSearch.getItemName().equals(" ")){
                            items = repository.findAll();
                        }else {
                            items = repository.item(itemSearch.getItemName());
                        }
                    } else if (itemSearch.getSelect().equals("3")) {
                        log.info("계약일검색");
                        items = repository.input(itemSearch.getInput());
                    }else if (itemSearch.getSelect().equals("4")) {
                        log.info("납품일검색");
                        items = repository.output(itemSearch.getOutput());
                    }else if (itemSearch.getSelect().equals("5")) {
                        log.info("상태 검색");
                        items = repository.status(itemSearch.getStatus());
                    } else {
                        items = repository.findAll();
                    }

                }
        List<As> proceeding = new ArrayList<>();

        for(As i : items){
            String[] split = i.getStatus().split("/");
            String s = split[split.length - 1];
            String[] getStat = s.split(",");
            String statResult = "A/S 접수";
            if(getStat.length>1){
                statResult= statusChange(getStat[1]);
            }
            i.setStatus(statResult);
            proceeding.add(i);

        }
        Collections.reverse(proceeding);
        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), proceeding.size());
        Page<As> userPage = new PageImpl<>(proceeding.subList(start, end), pageRequest, proceeding.size());

        model.addAttribute("items", userPage);
        model.addAttribute("member", loginMember);
        model.addAttribute("maxPage", 10);
        model.addAttribute("thisP",page+1);
       // model.addAttribute("member",member);

        return "as/asHome";
    }
    @ResponseBody
    @GetMapping("/role")
    public String role() {
        Role role = roleRepository.findById("RV");
        JSONObject obj = new JSONObject();
        try {
            obj.put("main",role.getAs());
            obj.put("serve",role.getAsS());

            log.info("json 출력 : {}",obj.toString());
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model,@Login Member loginMember) {
        //로그인 여부 체크
        As item = repository.findById(itemId);
        String status = item.getStatus();
        String[] split = status.split("/");
        String stat = split[split.length - 1];
        String[] resultS = stat.split(",");
        String result = "A/S 접수";
        if(resultS.length>1){
            result = statusChange(resultS[1]);
        }


        log.info("status = {}",result);
        item.setStatus(result);
        model.addAttribute("item", item);
        model.addAttribute("member",loginMember);
        return "as/asItem";
    }
    @ResponseBody
    @GetMapping("{itemId}/cal")
    public String itemForCal(@PathVariable long itemId) {
        //로그인 여부 체크
        As item = repository.findById(itemId);
        String status = item.getStatus();
        String end = item.getOutput();
        String[] statArr = status.split("/");;


        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
          for (int i = 0 ; i< statArr.length;i++)//배열
          {
              if(i==0){
                  JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                  sObject.put("sDate",item.getInput());
                  sObject.put("sStat", "A/S 접수");

                  jArray.put(sObject);
              }else{
                  log.info("i = {}",statArr[i]);
                  String[] stats = statArr[i].split(",");

                  String result = statusChange(stats[1]);


                  String modi = stats[0];
                  LocalDate date = LocalDate.parse(modi, DateTimeFormatter.ofPattern("yyMMdd"));
                  String change = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                  JSONObject sObject = new JSONObject();//배열 내에 들어갈 json

                  sObject.put("sDate",change);
                  sObject.put("sStat", result);

                  jArray.put(sObject);
              }

            }


            // 문자열을 LocalDate로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            char c = item.getStatus().charAt(item.getStatus().length() - 1);
            String output;
            if(c=='9'){
                String[] split = item.getStatus().split("/");
                String[] last = split[split.length - 1].split(",");
                String temp = "20"+last[0];
                StringBuffer sb = new StringBuffer();
                sb.append(temp);
                sb.insert(4, "-");
                sb.insert(7, "-");
                output = sb.toString();
            }
            else {
                output = item.getOutput();
            }
            LocalDate date = LocalDate.parse(output, formatter);
            LocalDate nextDay = date.plusDays(1);

            obj.put("title",item.getCompany());//배열을 넣음
            obj.put("startJ",item.getInput());//배열을 넣음
            obj.put("end",nextDay);//배열을 넣음
            obj.put("stats",jArray);
            obj.put("outPut",end);
            log.info("json 출력 : {}",obj.toString());
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
    }
    @GetMapping("/add")
    public String addForm(Model model,@Login Member loginMember,@RequestParam("serial") String serial,@RequestParam("company") String company) {
        log.info("serial={},com={}",serial,company);
        As as = new As();
        as.setCompany(company);
        as.setSerial(serial);

        String drone = serial.substring(0,5);
        if(drone.charAt(drone.length()-1)=='2'){
            log.info("knx2");
            as.setItem("KnX2");
        }else {
            log.info("knx");
            as.setItem("KnX");
        }
        //로그인 여부 체크
        model.addAttribute("as", as);
        model.addAttribute("member", loginMember);
        model.addAttribute("serialP",serial);
        model.addAttribute("companyP",company);
        List<Member> managers = memberRepository.managers();
        ArrayList<Member> simples = new ArrayList<>();
        for(int i=0;i<managers.size();i++){
            Member member = managers.get(i);
            Member simple = new Member(member.getId(),member.getName());
            simples.add(simple);
        }
        model.addAttribute("simples",simples);
        return "as/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("as") As form, BindingResult bindingResult, RedirectAttributes redirectAttributes,@Login Member loginMember,Model model, @RequestParam("id") long id) {

        model.addAttribute("member", loginMember);
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "as/addForm";
        }
        LocalDate currentDate = LocalDate.now();
        log.info(form.getItem());
        //성공 로직
        As item = new As(form.getCompany(),form.getItem(),String.valueOf(currentDate),form.getOutput(),"1","x",form.getSerial());

        As saveAs = repository.save(item);

        itemRepository.completeUpdate(id);

        redirectAttributes.addAttribute("itemId", saveAs.getId());
        return "redirect:/as/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        As item = repository.findById(itemId);
        String[] split = item.getStatus().split("/");
        String s = split[split.length - 1];
        String[] getStat = s.split(",");
        String statResult = "A/S 접수";
        if(getStat.length>1){
            statResult= statusChange(getStat[1]);
        }
        AsUpdateParam param = new AsUpdateParam();
        if(!item.getDelay().equals("x")){
            param.setDelay(item.getDelay());
        }


        model.addAttribute("as", item);
        model.addAttribute("asForm", param);
        model.addAttribute("stat", statResult);
        List<Member> managers = memberRepository.managers();
        ArrayList<Member> simples = new ArrayList<>();
        for(int i=0;i<managers.size();i++){
            Member member = managers.get(i);
            Member simple = new Member(member.getId(),member.getName());
            simples.add(simple);
        }
        model.addAttribute("simples",simples);
        return "as/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("asForm") AsUpdateParam form, BindingResult bindingResult) throws ParseException {
        LocalDate currentDate = LocalDate.now();
        form.setDate(String.valueOf(currentDate));

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "as/editForm";
        }
        As find = repository.findById(itemId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date output = dateFormat.parse(find.getOutput());
        Date realOut = dateFormat.parse(form.getDate());


        if(form.getStatus().equals("9")&&realOut.before(output)){
            log.info("이른 출고");
        }
        if(form.getDelay()!=null&&!form.getDelay().equals("")){
            repository.delay(itemId,form);
        }

        repository.update(itemId,form);

        return "redirect:/as/{itemId}";
    }

    public String statusChange(String result){
        if (result.equals("1")) {
            result = "LOG 분석 및 원인파악";
        }else if (result.equals("2")) {
            result = "1차 수리";
        }else if (result.equals("3")) {
            result = "1차 비행테스트";
        }else if (result.equals("4")) {
            result = "2차 수리";
        }else if (result.equals("5")) {
            result = "2차 비행테스트";
        }else if (result.equals("6")) {
            result = "3차 수리";
        }else if (result.equals("7")) {
            result = "3차 비행테스트";
        }else if (result.equals("8")) {
            result = "배송준비[결제 및 서류 전달]";
        }else if (result.equals("9")) {
            result = "출고";
        }
        return result;
    }


}
