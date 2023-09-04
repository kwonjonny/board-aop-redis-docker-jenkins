package board.mybatis.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import board.mybatis.mvc.annotation.RoleAdmin;
import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.service.NoticeService;
import board.mybatis.mvc.util.ManagementCookie;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

/**
 * {@code NoticeController}는 공지사항 관련 기능을 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@Controller
@RequestMapping("spring/notice/")
public class NoticeController {

    private final NoticeService noticeService;
    private final ManagementCookie managementCookie;

    /**
     * 생성자를 통한 의존성 주입
     *
     * @param noticeService    공지사항 서비스
     * @param managementCookie 쿠키 관리 유틸리티
     */
    @Autowired
    public NoticeController(NoticeService noticeService, ManagementCookie managementCookie) {
        log.info("Inject NoticeServices");
        this.noticeService = noticeService;
        this.managementCookie = managementCookie;
    }

    // GET | Create Notice
    @RoleAdmin
    @GetMapping("create")
    public String getCreateNotice(Authentication authentication) {
        log.info("GET | Create Notice Controller");
        return "spring/notice/create";
    }

    // GET | Read Notice
    @GetMapping("read/{nno}")
    public String getReadNotice(@PathVariable("nno") final Long nno, Model model, PageRequestDTO pageRequestDTO,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("GET | Read Notice Contoller");
        if (managementCookie.createCookie(request, response, nno)) {
            log.info("Making Cookie");
            noticeService.countViewNotice(nno);
        }
        NoticeDTO list = noticeService.readNotice(nno);
        model.addAttribute("list", list);
        return "spring/notice/read";
    }

    // GET | Update Notice
    @RoleAdmin
    @GetMapping("update/{nno}")
    public String getNoticeUpdate(@PathVariable("nno") final Long nno, Model model, PageRequestDTO pageRequestDTO,
            Authentication authentication) {
        log.info("GET | Update Notice Controller");
        NoticeDTO list = noticeService.readNotice(nno);
        model.addAttribute("list", list);
        return "spring/notice/update";
    }

    // GET | List Notice
    @GetMapping("list")
    public String getListNotice(PageRequestDTO pageRequestDTO, Model model) {
        log.info("GET | List Notice Controller");
        PageResponseDTO<NoticeListDTO> list = noticeService.listNotice(pageRequestDTO);
        model.addAttribute("list", list);
        return "spring/notice/list";
    }

    // POST | Create Notice
    @RoleAdmin
    @PostMapping("create")
    public String postCreateNotice(@Valid NoticeCreateDTO noticeCreateDTO, RedirectAttributes redirectAttributes,
            Authentication authentication) {
        log.info("POST | Create Notice Controller");
        Long createNotice = noticeService.createNotice(noticeCreateDTO);
        redirectAttributes.addFlashAttribute("message", "공지사항 게시글 생성 완료.");
        return "redirect:/spring/notice/list";
    }

    // POST | Update Notice
    @RoleAdmin
    @PostMapping("update")
    public String postUpdateNotice(@Valid NoticeUpdateDTO noticeUpdateDTO, RedirectAttributes redirectAttributes,
            Authentication authentication) {
        log.info("POST | Update Notice Controller");
        Long updateNotice = noticeService.updateNotice(noticeUpdateDTO);
        redirectAttributes.addFlashAttribute("message", "공지사항 게시글 업데이트 완료");
        return "redirect:/spring/notice/read/" + noticeUpdateDTO.getNno();
    }

    // POST | Delete Notice
    @RoleAdmin
    @PostMapping("delete/{nno}")
    public String postDeleteNotice(@PathVariable("nno") final Long nno, RedirectAttributes redirectAttributes,
            Authentication authentication) {
        log.info("POST | Delete Notice Controller");
        Long deleteNotice = noticeService.deleteNotice(nno);
        redirectAttributes.addFlashAttribute("message", "공지사항 게시글 삭제 완료.");
        return "redirect:/spring/notice/list";
    }
}
