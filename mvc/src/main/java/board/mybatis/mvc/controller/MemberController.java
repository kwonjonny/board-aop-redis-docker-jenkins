package board.mybatis.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

// Member Controller Class
@Log4j2
@Controller
@RequestMapping("/member/")
public class MemberController {

    // 의존성 주입
    private final MemberService memberService;

    // Autowired 명시적 표시
    @Autowired
    public MemberController(MemberService memberService) {
        log.info("Inject MemberService");
        this.memberService = memberService;
    }

    // GET : Create Member
    @GetMapping("create")
    public String getCreateMember() {
        log.info("GET | Create Member Controller");
        return "/member/create";
    }

    // GET : Read Member
    @GetMapping("read/{email}")
    public String getReadMember(@PathVariable("email") final String email, Model model, PageRequestDTO pageRequestDTO) {
        log.info("GET | Read Member Controller");
        MemberConvertDTO list = memberService.readMember(email);
        model.addAttribute("list", list);
        return "/member/read";
    }

    // GET : Update Member
    @GetMapping("update/{email}")
    public String geteUpdateMember(@PathVariable("email") final String email, Model model, PageRequestDTO pageRequestDTO) {
        log.info("GET | Update Member Controller");
        MemberConvertDTO list = memberService.readMember(email);
        model.addAttribute("list", list);
        return "/member/update";
    }

    // GET | List Member
    @GetMapping("list")
    public String getListMember(PageRequestDTO pageRequestDTO, Model model) {
        log.info("GET | List Member Controller");
        PageResponseDTO<MemberListDTO> list = memberService.listMember(pageRequestDTO);
        model.addAttribute("list", list);
        return "/member/list";
    }

    // POST | Create Member
    @PostMapping("create")
    public String postCreateMember(@Valid MemberCreateDTO memberCreateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Create Member Controller");
        Long createMember = memberService.joinMember(memberCreateDTO);
        redirectAttributes.addFlashAttribute("message", "회원 가입 완료.");
        return "redirect:/member/index";
    }

    // POST | Update Member
    @PostMapping("update")
    public String postUpdateMember(@Valid MemberUpdateDTO memberUpdateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Update Member Controller");
        Long updateMember = memberService.updateMember(memberUpdateDTO);
        redirectAttributes.addFlashAttribute("message", "회원 업데이트 완료.");
        return "redirect:/member/read/" + memberUpdateDTO.getEmail();
    }

    // POST | Delete Member 
    @PostMapping("delete/{email}")
    public String postDeleteMember(@PathVariable("email") final String email, RedirectAttributes redirectAttributes) {
        log.info("POST | Delete Member Controller");
        Long deleteMember = memberService.deleteMember(email);
        redirectAttributes.addFlashAttribute("mesage", "회원 탈퇴 완료.");
        return "redirect:/member/index";
    }
}
