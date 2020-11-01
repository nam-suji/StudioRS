package photo.photoStudio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import photo.photoStudio.controller.form.MemberLoginForm;
import photo.photoStudio.domain.Member;
import photo.photoStudio.exception.UserNotFoundException;
import photo.photoStudio.service.MemberService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final MemberService memberService;
    String href = "";

    @GetMapping("/admin/login")
    public String loginForm(Model model){
        model.addAttribute("adminLoginForm", new MemberLoginForm());
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String createLogin(@Valid MemberLoginForm loginForm, BindingResult result, HttpSession session) throws UserNotFoundException {
        if(result.hasErrors()){
            System.out.println("안 들어옴");
            System.out.println(loginForm.getEmail()+":"+loginForm.getPwd());
            return "admin/login";
        }
        Member member = memberService.findEmailPwd(loginForm.getEmail(), loginForm.getPwd());
        if(member!=null&&member.getId()==1){
            System.out.println(member.getId());
            session.setAttribute("id",member.getId().toString());
            href = "redirect:/admin/home";
        }else{
            href = "redirect:/admin/login";
        }

        return href;
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session){
        session.removeAttribute("id");
        return "redirect:/"+"admin/home";
    }

    @GetMapping("/admin/members")
    public String List(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "admin/memberList";
    }
}
