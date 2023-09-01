package board.mybatis.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.service.BoardService;
import board.mybatis.mvc.util.ManagementCookie;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

// Board Controller Class
@Log4j2
@Controller
@RequestMapping("spring/board/")
@PreAuthorize("permitAll")
public class BoardController {

    // 의존성 주입
    private final BoardService boardService;
    private final ManagementCookie managementCookie;

    // Autowired 명시적 표시
    @Autowired
    public BoardController(BoardService boardService, ManagementCookie managementCookie) {
        log.info("Inject BoardService");
        this.boardService = boardService;
        this.managementCookie = managementCookie;
    }

    // GET | Create Board
    @GetMapping("create")
    public String getCreateBoard() {
        log.info("GET | Create Board Controller");
        return "spring/board/create";
    }

    // GET | Read Board
    @GetMapping("read/{bno}")
    public String getReadBoard(@PathVariable("bno") final Long bno, Model model, PageRequestDTO pageRequestDTO,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("GET | Read Board Controller");
        if (managementCookie.createCookie(request, response, bno)) {
            log.info("Making Cookie");
            boardService.countviewBoard(bno);
        }
        BoardDTO list = boardService.readBoard(bno);
        model.addAttribute("list", list);
        return "spring/board/read";
    }

    // GET | Update Board
    @GetMapping("update/{bno}")
    public String getUpdateBoard(@PathVariable("bno") final long bno, Model model, PageRequestDTO pageRequestDTO) {
        log.info("GET | Update Board Controller");
        BoardDTO list = boardService.readBoard(bno);
        model.addAttribute("list", list);
        return "spring/board/update";
    }

    // GET | List Board
    @GetMapping("list")
    public String getListBoard(PageRequestDTO pageRequestDTO, Model model) {
        log.info("GET | List Board Controller");
        PageResponseDTO<BoardListDTO> list = boardService.listBoard(pageRequestDTO);
        model.addAttribute("list", list);
        return "spring/board/list";
    }

    // POST | Create Board
    @PostMapping("create")
    public String postCreateBoard(@Valid BoardCreateDTO boardCreateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Create Board Controller");
        log.info("boardCreateDTO: " + boardCreateDTO);
        Long createBoard = boardService.createBoard(boardCreateDTO);
        redirectAttributes.addFlashAttribute("message", "게시물 생성 완료.");
        return "redirect:/spring/board/list";
    }

    // POST | Update Board
    @PostMapping("update")
    public String postUpdateBoard(@Valid BoardUpdateDTO boardUpdateDTO, RedirectAttributes redirectAttributes) {
        log.info("POST | Update Board Contoller");
        Long updateBoard = boardService.updateBoard(boardUpdateDTO);
        redirectAttributes.addFlashAttribute("message", "게시물 업데이트 완료.");
        return "redirect:/spring/board/read/" + boardUpdateDTO.getBno();
    }

    // POST | Delete Board
    @PostMapping("delete/{bno}")
    public String postDelteBoard(@PathVariable("bno") final Long bno, RedirectAttributes redirectAttributes) {
        log.info("POST | Delete Board Controller");
        Long deleteBoard = boardService.deleteBoard(bno);
        redirectAttributes.addFlashAttribute("message", "게시물 삭제 완료.");
        return "redirect:/spring/board/list";
    }
}
