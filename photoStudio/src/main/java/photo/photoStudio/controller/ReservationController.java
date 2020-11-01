package photo.photoStudio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import photo.photoStudio.domain.Item;
import photo.photoStudio.domain.Member;
import photo.photoStudio.domain.Reservation;
import photo.photoStudio.repository.ReservationSearch;
import photo.photoStudio.service.ItemService;
import photo.photoStudio.service.MemberService;
import photo.photoStudio.service.ReservationService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/reservation")
    public String createReservationForm(Model model, HttpSession session){
        String s = (String)session.getAttribute("id");
        //System.out.println(s);
        Long id = Long.parseLong(s);

        Member member = memberService.findOne(id);
        List<Item> items = itemService.findAll();
        List<String> time = new ArrayList<>();
        for(int i = 10; i<20;i++){
            String t = i + ":00";
            time.add(t);
        }

        model.addAttribute("time",time);
        model.addAttribute("member",member);
        model.addAttribute("items",items);

        return "reservations/reservationForm";
    }

    @PostMapping("/reservation")
    public String createReservation(@RequestParam("id") Long memberId,
                              @RequestParam("itemId") Long itemId,
                              @RequestParam("phone") String phone,
                              @RequestParam("inputname") String inputname,
                              @RequestParam("date") String date,
                              @RequestParam("time") String time){

        Long reservationId = reservationService.saveReservation(memberId,itemId,phone,date,time,inputname);
        if(reservationId==null){
            return "reservations/reservationForm";
        }

        return "redirect:/";
    }

    @GetMapping("/reservations/{id}")
    public String reservationList(@PathVariable("id") Long id, Model model){

        List<Reservation> reservations = reservationService.findReservations(id);
        model.addAttribute("reservations",reservations);

        return "reservations/reservationList";
    }

    @PostMapping("/reservations/{reservationId}/cancel")
    public String cancelReservation(@PathVariable("reservationId")Long id){
        reservationService.cancel(id);
        return "redirect:/";
    }

    @GetMapping("/admin/reservations")
    public String adminReservationList(@ModelAttribute("reservationSearch") ReservationSearch reservationSearch,
                                       Model model){
        List<Reservation> reservations = reservationService.findAdminReservations(reservationSearch);
        model.addAttribute("reservations",reservations);

        return "admin/reservationList";
    }

    @PostMapping("/admin/reservations/{id}/input0")
    public String checkInput(@PathVariable("id")Long id){
        reservationService.checkInput(id);
        return "redirect:/admin/home";
    }
}
