package board.mybatis.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

// Notice Controller Class
@Log4j2
@Controller
@RequestMapping("/notice/")
public class NoticeController {

    // 의존성주입
    private final NoticeService noticeService;
    private final ManagementCookie managementCookie;

    // Autowired 명시적 표시
    @Autowired
    public NoticeController(NoticeService noticeService, ManagementCookie managementCookie) {
        log.info("Inject NoticeServices");
        this.noticeService = noticeService;
        this.managementCookie = managementCookie;
    }

    // GET | Create Notice
    @GetMapping("create")
    public String getCreateNotice() {
        log.info("GET | Create Notice Controller");
        return "/notice/create";
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
        return "/notice/read";
    }

    // GET | Update Notice
    @GetMapping("update/{nno}")
    public String getNoticeUpdate(@PathVariable("nno") final Long nno, Model model, PageRequestDTO pageRequestDTO) {
        log.info("GET | Update Notice Controller");
        NoticeDTO list = noticeService.readNotice(nno);
        model.addAttribute("list", list);
        return "/notice/update";
    }

    // GET | List Notice
    @GetMapping("list")
    public String getListNotice(PageRequestDTO pageRequestDTO, Model model) {
        log.info("GET | List Notice Controller");
        PageResponseDTO<NoticeListDTO> list = noticeService.listNotice(pageRequestDTO);
        model.addAttribute("list", list);
        return "/notice/list";
    }

    // POST | Create Notice
    @PostMapping("create")
    public String postCreateNotice(@Valid NoticeCreateDTO noticeCreateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Create Notice Controller");
        Long nno = noticeService.createNotice(noticeCreateDTO);
        redirectAttributes.addFlashAttribute("message", "공지사항 게시글 생성 완료.");
        return "redirect:/notice/list";
    }

    // POST | Update Notice
    @PostMapping("update")
    public String postUpdateNotice(@Valid NoticeUpdateDTO noticeUpdateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Update Notice Controller");
        Long nno = noticeService.updateNotice(noticeUpdateDTO);
        redirectAttributes.addFlashAttribute("mesage", "공지사항 게시글 업데이트 완료");
        return "redirect:/notice/read/" + noticeUpdateDTO.getNno();
    }

    // POST | Delete Notice
    @PostMapping("delete/{nno}")
    public String postDeleteNotice(@PathVariable("nno") final Long nno, RedirectAttributes redirectAttributes) {
        log.info("POST | Delete Notice Controller");
        Long deleteNno = noticeService.deleteNotice(nno);
        redirectAttributes.addFlashAttribute("message", "공지사항 게시글 삭제 완료.");
        return "redirect:/notice/list";
    }
}
