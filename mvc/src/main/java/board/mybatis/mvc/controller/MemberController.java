package board.mybatis.mvc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import board.mybatis.mvc.annotation.role.CheckMemberMatch;
import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.service.EmailService;
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

/**
 * {@code MemberController}는 회원 관련 기능을 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@Controller
@RequestMapping("spring/member/")
@Tag(name = "Member API", description = "회원과 관련된 모든 API")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    /**
     * 생성자를 통한 의존성 주입
     *
     * @param memberService 회원 서비스
     * @param emailService  이메일 서비스
     */
    @Autowired
    public MemberController(MemberService memberService, EmailService emailService) {
        log.info("Inject MemberService");
        this.memberService = memberService;
        this.emailService = emailService;
    }

    // GET : Create Member
    @GetMapping("create")
    @Operation(summary = "회원 생성 페이지 조회", description = "회원을 생성하는 페이지를 조회합니다.")
    public String getCreateMember() {
        log.info("GET | Create Member Controller");
        return "spring/member/create";
    }

    // GET : Read Member
    @GetMapping("read/{email}")
    @Operation(summary = "회원 조회", description = "특정 회원을 조회합니다.")
    public String getReadMember(
            @Parameter(description = "회원 이메일", required = true) @PathVariable("email") final String email,
            Model model, PageRequestDTO pageRequestDTO) {
        log.info("GET | Read Member Controller");
        MemberConvertDTO list = memberService.readMember(email);
        model.addAttribute("list", list);
        return "spring/member/read";
    }

    // GET : Update Member
    @CheckMemberMatch
    @GetMapping("update/{email}")
    @Operation(summary = "회원 수정 페이지 조회", description = "회원 정보를 수정하는 페이지를 조회합니다.")
    public String geteUpdateMember(
            @Parameter(description = "회원 이메일", required = true) @PathVariable("email") final String email,
            Model model, PageRequestDTO pageRequestDTO, Authentication authentication) {
        log.info("GET | Update Member Controller");
        MemberConvertDTO list = memberService.readMember(email);
        model.addAttribute("list", list);
        return "spring/member/update";
    }

    // GET | List Member
    @GetMapping("list")
    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회합니다.")
    public String getListMember(@Parameter(description = "페이지 정보") PageRequestDTO pageRequestDTO, Model model) {
        log.info("GET | List Member Controller");
        PageResponseDTO<MemberListDTO> list = memberService.listMember(pageRequestDTO);
        model.addAttribute("list", list);
        return "spring/member/list";
    }

    // GET | Duplicate Member Email
    @GetMapping("duplicate/{email}")
    @Operation(summary = "중복 이메일 확인", description = "이메일 중복 여부를 확인합니다.")
    public ResponseEntity<Map<String, Object>> checkDuplicateEmail(
            @Parameter(description = "회원 이메일", required = true) @PathVariable("email") final String email) {
        log.info("GET | Checking For Duplicate Email Controller");
        Long isDuplicate = memberService.duplicateEmail(email);
        return new ResponseEntity<>(Map.of("isDuplicate", isDuplicate), HttpStatus.OK);
    }

    // GET | Verified Member Email
    @GetMapping("verify")
    @Operation(summary = "회원 가입 이메일 인증", description = "회원의 이메일 인증을 완료합니다.")
    public String getVerifyEmail(
            @Parameter(description = "회원 이메일", required = true) @RequestParam("email") final String email,
            RedirectAttributes redirectAttributes) {
        log.info("GET | Verify Member Email Controller");
        memberService.verifyEmail(email);
        redirectAttributes.addFlashAttribute("message", "이메일 인증 완료! 로그인해주세요.");
        return "redirect:/spring/index";
    }

    // GET | Forgot Member Password
    @GetMapping("forgot/password")
    @Operation(summary = "회원 패스워드 페이지 조회", description = "회원의 패스워드 재 설정 이메일 페이지를 조회합니다.")
    public String getForgotMemberPassword() {
        log.info("GET | Forgot Member Password Controller");
        return "spring/member/password";
    }

    // POST | Forgot Member Password
    @PostMapping("forgot/password")
    @Operation(summary = "회원 패스워드 재설정 이메일", description = "회원의 패스워드 재 설정 이메일 발송합니다.")
    public String postForgotMemberPassword(
            @Parameter(description = "회원 이메일", required = true) @RequestParam("email") String email,
            RedirectAttributes redirectAttributes) {
        log.info("POST | Forgot Member Password Controller");
        emailService.sendPasswordResetMail(email);
        redirectAttributes.addFlashAttribute("message", "회원 패스워드 재 설정 이메일 전송.");
        return "spring/index";
    }

    // POST | Create Member
    @PostMapping("create")
    @Operation(summary = "회원 생성", description = "새로운 회원을 생성하고 이메일 인증 코드를 발송합니다.")
    public String postCreateMember(
            @Valid MemberCreateDTO memberCreateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Create Member Controller");
        Long createMember = memberService.joinMember(memberCreateDTO);
        emailService.sendCreateMail(memberCreateDTO.getEmail());
        redirectAttributes.addFlashAttribute("message", "회원 가입 완료 이메일 인증을 완료해주세요.");
        return "redirect:/spring/index";
    }

    // POST | Update Member
    @CheckMemberMatch
    @PostMapping("update")
    @Operation(summary = "회원 업데이트", description = "기존 회원 정보를 업데이트합니다.")
    public String postUpdateMember(
            @Valid MemberUpdateDTO memberUpdateDTO, RedirectAttributes redirectAttributes,
            Authentication authentication) {
        log.info("POST | Update Member Controller");
        Long updateMember = memberService.updateMember(memberUpdateDTO);
        redirectAttributes.addFlashAttribute("message", "회원 업데이트 완료.");
        return "redirect:/spring/member/read/" + memberUpdateDTO.getEmail();
    }

    // POST | Delete Member
    @CheckMemberMatch
    @PostMapping("delete/{email}")
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴시키고 탈퇴 이메일을 전송합니다.")
    public String postDeleteMember(
            @Parameter(description = "회원 이메일", required = true) @PathVariable("email") final String email,
            RedirectAttributes redirectAttributes, Authentication authentication) {
        log.info("POST | Delete Member Controller");
        Long deleteMember = memberService.deleteMember(email);
        emailService.sendDeleteMail(email);
        redirectAttributes.addFlashAttribute("message", "회원 탈퇴 완료 및 탈퇴 이메일 전송.");
        return "redirect:/spring/index";
    }
}