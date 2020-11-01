package photo.photoStudio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import photo.photoStudio.controller.form.MemberForm;
import photo.photoStudio.controller.form.MemberLoginForm;
import photo.photoStudio.domain.Member;
import photo.photoStudio.exception.UserNotFoundException;
import photo.photoStudio.service.MemberService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/loginForm")
    public String createLoginForm(Model model){
        model.addAttribute("memberLoginForm", new MemberLoginForm());
        return "members/loginForm";
    }

    @PostMapping("/members/loginForm")
    public String createLogin(@Valid MemberLoginForm loginForm, BindingResult result, HttpSession session) throws UserNotFoundException {
        if(result.hasErrors()){
            //System.out.println("안 들어옴");
            System.out.println(loginForm.getEmail()+":"+loginForm.getPwd());
            return "members/loginForm";
        }
        Member member = memberService.findEmailPwd(loginForm.getEmail(), loginForm.getPwd());
        if(member!=null){
            System.out.println(member.getId());
            session.setAttribute("id",member.getId().toString());
        }else {
            return "members/loginForm";
        }

        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpSession session){
        session.removeAttribute("id");
        return "redirect:/";
    }

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        if(result.hasErrors()){
            //System.out.println("안 들어옴");
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setMember(memberForm.getEmail(), memberForm.getPwd(), memberForm.getName(), memberForm.getPhone());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members/{id}/edit")
    public String updateMemberForm(@PathVariable("id") Long id, Model model){
        Member member = (Member) memberService.findOne(id);

        MemberForm form = new MemberForm();
        form.updateSetMember(member.getId(), member.getEmail(), member.getPwd(), member.getName(), member.getPhone());

        model.addAttribute("form",form);
        return "members/updateMemberForm";
    }

    @PostMapping("/members/{id}/edit")
    public String updateMember(@ModelAttribute("form") MemberForm form){
        memberService.updateMember(form.getId(), form.getPwd(), form.getName(), form.getPhone());
        return "redirect:/";
    }
}
