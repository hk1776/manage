package com.example.manage.web.flight;

import com.example.manage.domain.as.As;
import com.example.manage.domain.flight.Flight;
import com.example.manage.domain.flight.FlightRepository;
import com.example.manage.domain.item.Item;
import com.example.manage.domain.member.Member;
import com.example.manage.domain.member.MemberRepository;
import com.example.manage.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {

    private final MemberRepository memberRepository;
    private final FlightRepository repository;

    @GetMapping
    public String home(@Login Member loginMember, Model model){
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("flight",new Flight());
        model.addAttribute("member", loginMember);

        return "schedule/flight";
    }
    @ResponseBody
    @GetMapping("/cal")
    public String itemForCal() {
        //로그인 여부 체크
        List<Flight> all = repository.findAll();
        List<Member> managers = memberRepository.managers();
        String names ="";
        for (Member i : managers){
            names+= ","+i.getName();
        }
        names = names.substring(1);
        ArrayList<Member> simples = new ArrayList<>();
        for(int i=0;i<managers.size();i++){
            Member member = managers.get(i);
            Member simple = new Member(member.getId(),member.getName());
            simples.add(simple);
        }

        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < all.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("id",all.get(i).getId());
                sObject.put("people",all.get(i).getPeople());
                sObject.put("type", all.get(i).getType());
                sObject.put("location",all.get(i).getLocation());
                sObject.put("item",all.get(i).getItem());
                sObject.put("start",all.get(i).getStart());
                sObject.put("end",all.get(i).getEnd()); //납품에도 똑같이해야함
                jArray.put(sObject);
            }
            obj.put("simples",names);
            obj.put("fInfo", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
    }
    @ResponseBody
    @PostMapping("/save")
    public void requestBodyJson(@RequestBody FlightInput flight){
        //return repository.save(flight);
        log.info("받은 값={},String ={}",flight.getFlightList().size());
        int index = 0;
        for(Flight i : flight.getFlightList()){
            log.info("index = {}",index);
            if(i!=null) {
                log.info("id={} , start={}", i.getId(), i.getStart());
                if(i.getStart().length()>11){
                    if(i.getStart().contains("00:00")&&i.getEnd().contains("00:00")){
                        i.setStart(i.getStart().substring(0,10));
                        i.setEnd( i.getEnd().substring(0,10));
                    }
                    repository.save(i);
                }
            }
            index++;
        }
        if(flight.getDelete()!=null){
            for(int i : flight.getDelete()){
                repository.delete(i);
            }
        }

    }
}
