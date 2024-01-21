package com.example.manage.web.knDrone;

import com.example.manage.domain.as.As;
import com.example.manage.domain.as.AsRepository;
import com.example.manage.domain.delivery.Delivery;
import com.example.manage.domain.delivery.DeliveryRepository;
import com.example.manage.domain.item.Item;
import com.example.manage.domain.item.ItemRepository;
import com.example.manage.domain.item.ItemSearchCond;
import com.example.manage.domain.knDrone.*;
import com.example.manage.domain.knDrone.version.Version;
import com.example.manage.domain.knDrone.version.VersionRepository;
import com.example.manage.domain.member.Member;
import com.example.manage.domain.member.MemberRepository;
import com.example.manage.web.argumentresolver.Login;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/drone")
@RequiredArgsConstructor
public class DroneController {
    private final MemberRepository memberRepository;
    private final DroneInfoRepository repository;
    private final VersionRepository versionRepository;
    private final DeliveryRepository deliveryRepository;
    private final ItemRepository itemRepository;
    private final AsRepository asRepository;



    @GetMapping
    public String drone(@Login Member loginMember, Model model, @ModelAttribute("itemSearch") DroneSearchCond searchCond) {
        //로그인 여부 체크
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "drone/items";
    }

    @GetMapping("/search")
    public String search(@Login Member loginMember, Model model, @ModelAttribute("itemSearch") DroneSearchCond searchCond, @RequestParam(defaultValue = "0") int page) {
        //로그인 여부 체크
        if (loginMember == null) {
            return "home";
        }
        List<DroneInfo> items = new ArrayList<>();

        if(searchCond.getSelect().equals("1")){
            log.info("식 시리얼");
                items = repository.serial(searchCond.getSerial());

        } else if (searchCond.getSelect().equals("2")) {
            log.info("기체 시리얼");
                items = repository.drone(searchCond.getDrone());

        } else if (searchCond.getSelect().equals("3")) {
            log.info("회사검색");
                items = repository.company(searchCond.getCompany());

        }

        Collections.reverse(items);
        PageRequest pageRequest = PageRequest.of(page, 10);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), items.size());
        Page<DroneInfo> userPage = new PageImpl<>(items.subList(start, end), pageRequest, items.size());
        log.info("selectV={}",searchCond.getSelect());
        model.addAttribute("select",searchCond.getSelect());
        model.addAttribute("items", userPage);
        model.addAttribute("member", loginMember);
        model.addAttribute("maxPage", 10);
        model.addAttribute("thisP",page+1);
        // model.addAttribute("member",member);

        return "drone/search";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable String itemId, Model model, @Login Member loginMember) {
        if (loginMember == null) {
            return "home";
        }
        //로그인 여부 체크
        DroneInfo item = repository.findById(itemId);
        List<Item> items = itemRepository.findBySerial(itemId);
        List<Delivery> delivery = deliveryRepository.findBySerial(itemId);
        List<As> as = asRepository.findBySerial(itemId);

        model.addAttribute("items", items);
        model.addAttribute("as", as);
        model.addAttribute("delivery", delivery);

        model.addAttribute("item", item);
        model.addAttribute("member",loginMember);
        return "drone/item";
    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable String itemId, Model model, @Login Member loginMember,@RequestParam("id") Long id) {
        if (loginMember == null) {
            return "home";
        }
        //로그인 여부 체크
        DroneInfo item = repository.findById(itemId);
        DroneInfo now = repository.findById("1");

        String drone = item.getSerial().substring(0,5);

        Version version;
        if(drone.charAt(drone.length()-1)=='2'){
            log.info("knx2");
            version = versionRepository.findById("KnX2");
        }else {
            log.info("knx");
            version = versionRepository.findById("KnX");
        }
        model.addAttribute("id",id);
        model.addAttribute("version",version);
        model.addAttribute("num",now);
        model.addAttribute("item", item);
        model.addAttribute("member",loginMember);
        return "drone/parts";
    }
    @PostMapping("/{itemId}/edit")
    public String editPost(@PathVariable String itemId, RedirectAttributes redirectAttributes, @Login Member loginMember, Model model,@RequestBody DroneUpdateParam data,@RequestParam("id") Long id) {
        model.addAttribute("member", loginMember);

        DroneInfo drone = repository.findById(itemId);
        repository.update(drone,data);

        if(id!=0){
            String serial = drone.getSerial();
            String dType;
            if(serial.charAt(serial.length()-1)=='2'){
                log.info("knx2");
                dType = "KnX2";
            }else {
                log.info("knx");
                dType = "KnX";
            }
            String droneS = drone.getDrone();
            String rtk;
            if(droneS.contains("R")){
                rtk= "RTK";
            }else {
                rtk = "없음";
            }

            itemRepository.completeUpdate(id);
            Delivery delivery = new Delivery(data.getInput(),data.getOutput(),drone.getCompany(),dType,"RC2",rtk,String.valueOf(0),"1","x",itemId);
            Delivery deliResult = deliveryRepository.save(delivery);
            redirectAttributes.addAttribute("itemId",deliResult.getId());
            return "redirect:/delivery/{itemId}";
        }else {
            redirectAttributes.addAttribute("itemId",itemId);
            return "redirect:/drone/{itemId}";
        }

    }

    @GetMapping("/manage")
    public String manage(Model model, @Login Member loginMember) {
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "drone/drone";
    }
    @GetMapping("/add")
    public String addForm(Model model,@Login Member loginMember,@RequestParam("id") Long itemId) {
        if (loginMember == null) {
            return "home";
        }

        DroneInfo num = repository.findById("1");
        log.info("idCheck={},numCheck={}",itemId,num.getSerial());
        Version knX2 = versionRepository.findById("KnX2");
        model.addAttribute("drone", new DroneInfo());
        model.addAttribute("version",knX2);
        model.addAttribute("num",num);//식 시리얼 넘버는 바인딩으로 대체
        model.addAttribute("member", loginMember);
        return "drone/addForm";
    }

    @PostMapping("/add")
    public String addItem( RedirectAttributes redirectAttributes, @Login Member loginMember, Model model,@RequestBody DroneInfoParam data,@RequestParam("id") Long itemId) {
        model.addAttribute("member", loginMember);
        //성공 로직
        log.info(data.getDrone());

        boolean dType =false;
        boolean rType = false;

        String serial = "";
        if(data.getSNum()==1){
            char rtkCheck = data.getDrone().charAt(4);

            if(!data.getKeyInput().equals("x")){
                dType = true;
            }
            if(rtkCheck=='R'){
                rType = true;
            }
           DroneInfo drone = new DroneInfo(data.getSerial(),0,data.getDrone(),data.getBinding(),data.getController(),data.getBattery(),data.getCharger(),data.getTablet(),data.getRtk(),data.getKeyInput(),data.getBoard(),data.getFirmware(),data.getManual(),data.getNote(),data.getCompany(),data.getGimbal());
            serial = data.getSerial();
            repository.save(drone);
       }else {

            int serialC = Integer.parseInt(data.getSerial().substring(data.getSerial().length()-4));
            int droneC = Integer.parseInt(data.getDrone().substring(data.getDrone().length()-4));
            int controllerC = Integer.parseInt(data.getController().substring(data.getController().length()-4));
            int gimbalC = Integer.parseInt(data.getGimbal().substring(data.getGimbal().length()-4));

            String serialS = data.getSerial().substring(0,data.getSerial().length()-4);
            String droneS = data.getDrone().substring(0,data.getDrone().length()-4);
            String controllerS = data.getController().substring(0,data.getController().length()-4);
            String gimbalS = data.getGimbal().substring(0,data.getGimbal().length()-4);

            DroneInfo info = repository.findById("1");


            for(int i = 0; i<data.getSNum();i++){
                int bNow = Integer.parseInt(info.getBattery());
                int cNow = Integer.parseInt(info.getCharger());

                log.info("bNow={},cNow={}",bNow,cNow);

                String serial1 = serialS+String.format("%04d", serialC+i);
                String drone = droneS+String.format("%04d", droneC+i);
                String controller = controllerS+String.format("%04d", controllerC+i);
                String battery =batteryChanger(bNow,data.getBattery());
                String charger = batteryChanger(cNow,data.getCharger());
                String gimbal = gimbalS+String.format("%04d", gimbalC+i);
                String rtkInput = "x";
                if(!data.getRtk().equals("x")){
                    rtkInput = rtkChanger(i,data.getRtk());
                    rType = true;
                }

                String keyInput = "x";
                if(!data.getKeyInput().equals("x")){
                    keyInput = keyChanger(i,data.getKeyInput());
                    dType = true;
                }
                log.info("battery = {},Charger={}",battery,charger);
                DroneInfo droneInfo = new DroneInfo(serial1,0,drone,data.getBinding(),controller,battery,charger,data.getTablet(),rtkInput,keyInput,data.getBoard(),data.getFirmware(),data.getManual(),data.getNote(),data.getCompany(),gimbal);
                repository.save(droneInfo);
                serial+=serial1+",";
            }

        }
        String deliDrone = "KnX2";
        String deliRtk = "없음";
        if(dType){
            deliDrone = "KnX2-D";
        }
        if(rType){
            deliRtk = "RTK";
        }
        if(serial.length()>=20){
            serial = serial.substring(0, serial.length() - 1);
        }
        Delivery delivery = new Delivery(data.getInput(),data.getOutput(),data.getCompany(),deliDrone,"RC2",deliRtk,String.valueOf(data.getSNum()),"1","x",serial);
        Delivery deliResult = deliveryRepository.save(delivery);


        if(itemId!=0){
            itemRepository.completeUpdate(itemId);
            itemRepository.addSerial(itemId,serial);
        }

        redirectAttributes.addAttribute("itemId",deliResult.getId());
        return "redirect:/delivery/{itemId}";
    }

    public String batteryChanger(int now,String battery){

        String[] arr = battery.split("/");
        String result ="";

        String iSerial1 = arr[0].substring(0,arr[0].length()-4);

        for(int i=1;i<=arr.length;i++){
            //int iSerial2 = Integer.parseInt(arr[i].substring(arr[i].length() - 4));
            log.info("now = {}",now);
            result += iSerial1+String.format("%04d",(now+i))+"/";
        }
        return result.substring(0,result.length()-1);
    }

    public String keyChanger(int i,String key) {
        int keyInputC = Integer.parseInt(key.substring(key.length() - 4));
        String keyInputS = key.substring(0, key.length() - 4);
        String keyInput = keyInputS + String.format("%04d", keyInputC + i);
        return keyInput;
    }

    public String rtkChanger(int i,String rtk) {
        int rtkInputC = Integer.parseInt(rtk.substring(rtk.length() - 4));
        String rtkInputS = rtk.substring(0, rtk.length() - 4);
        String rtkInput = rtkInputS + String.format("%04d", rtkInputC + i);
        return rtkInput;
    }
}
