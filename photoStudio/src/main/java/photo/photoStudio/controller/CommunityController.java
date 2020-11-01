package photo.photoStudio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import photo.photoStudio.controller.form.CommunityForm;
import photo.photoStudio.domain.Community;
import photo.photoStudio.domain.Member;
import photo.photoStudio.domain.Pagination;
import photo.photoStudio.service.CommunityService;
import photo.photoStudio.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final MemberService memberService;


    /**
     * Notice
     * */
    @GetMapping("/notice")
    public String noticeList(Model model, @RequestParam(defaultValue = "1") int page){
        List<Community> communityList = communityService.findAll("n");
        int totalListCnt = communityList.size();

        Pagination pagination = new Pagination(totalListCnt, page);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        List<Community> communities = communityService.findPagingAll("n", startIndex, pageSize);

        model.addAttribute("communities", communities);
        model.addAttribute("pagination", pagination);

        return "community/noticeList";
    }

    @GetMapping("/notice/new")
    public String createNoticeForm(Model model){
        model.addAttribute("form", new CommunityForm());
        return "/community/createNotice";
    }

    @PostMapping("/notice/new")
    public String createNotice(HttpSession session, CommunityForm form){
        String s = (String)session.getAttribute("id");
        Long id = Long.parseLong(s);

        Member member = memberService.findOne(id);

        Community community = new Community();
        community.setCommunity(member.getId(), member.getName(), "n", form.getTitle(), form.getContent());

        communityService.saveCommunity(community);

        return "redirect:/notice";
    }

    @GetMapping("/notice/{communityId}")
    public String viewNotice(@PathVariable("communityId")Long id, Model model){
        Community community = communityService.findOne(id);

        CommunityForm form = new CommunityForm();
        form.setId(community.getId());
        form.setMemberId(community.getMemberId());
        form.setTitle(community.getTitle());
        form.setContent(community.getContent());

        model.addAttribute("form",form);
        return "community/noticeView";
    }

    @GetMapping("/notice/{communityId}/edit")
    public String updateNoticeForm(@PathVariable("communityId") Long id, Model model){
        Community community = communityService.findOne(id);

        CommunityForm form = new CommunityForm();
        form.setId(community.getId());
        form.setMemberId(community.getMemberId());
        form.setTitle(community.getTitle());
        form.setContent(community.getContent());

        model.addAttribute("form",form);
        return "/community/updateNotice";
    }

    @PostMapping("/notice/{communityId}/edit")
    public String updateNotice(@ModelAttribute("form") CommunityForm form){
        communityService.updateCommunity(form.getId(), form.getTitle(), form.getContent());
        return "redirect:/notice";
    }

    @RequestMapping("/notice/{communityId}/delete")
    public String deleteNotice(@PathVariable("communityId") Long id){
        communityService.deleteCommunity(id);
        return "redirect:/notice";
    }

    /**
     * QnA
     * */
    @GetMapping("/qna")
    public String qnaList(Model model, @RequestParam(defaultValue = "1") int page){
        List<Community> communityList = communityService.findAll("q");
        int totalListCnt = communityList.size();

        Pagination pagination = new Pagination(totalListCnt, page);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        List<Community> communities = communityService.findPagingAll("q", startIndex, pageSize);

        model.addAttribute("communities", communities);
        model.addAttribute("pagination", pagination);

        return "community/qnaList";
    }

    @GetMapping("/qna/new")
    public String createQnaForm(Model model){
        model.addAttribute("form", new CommunityForm());
        return "/community/createQna";
    }

    @PostMapping("/qna/new")
    public String createQna(HttpSession session, CommunityForm form){
        String s = (String)session.getAttribute("id");
        Long id = Long.parseLong(s);

        Member member = memberService.findOne(id);

        Community community = new Community();
        community.setCommunity(member.getId(), member.getName(), "q", form.getTitle(), form.getContent());

        communityService.saveCommunity(community);

        return "redirect:/qna";
    }

    @GetMapping("/qna/{communityId}")
    public String viewQna(@PathVariable("communityId")Long id, Model model){
        Community community = communityService.findOne(id);

        CommunityForm form = new CommunityForm();
        form.setId(community.getId());
        form.setMemberId(community.getMemberId());
        form.setTitle(community.getTitle());
        form.setContent(community.getContent());

        model.addAttribute("form",form);
        return "community/qnaView";
    }

    @GetMapping("/qna/{communityId}/edit")
    public String updateQnaForm(@PathVariable("communityId") Long id, Model model){
        Community community = communityService.findOne(id);

        CommunityForm form = new CommunityForm();
        form.setId(community.getId());
        form.setMemberId(community.getMemberId());
        form.setTitle(community.getTitle());
        form.setContent(community.getContent());

        model.addAttribute("form",form);
        return "/community/updateQna";
    }

    @PostMapping("/qna/{communityId}/edit")
    public String updateQna(@ModelAttribute("form") CommunityForm form){
        communityService.updateCommunity(form.getId(), form.getTitle(), form.getContent());
        return "redirect:/qna";
    }

    @RequestMapping("/qna/{communityId}/delete")
    public String deleteQna(@PathVariable("communityId") Long id){
        communityService.deleteCommunity(id);
        return "redirect:/qna";
    }
}
