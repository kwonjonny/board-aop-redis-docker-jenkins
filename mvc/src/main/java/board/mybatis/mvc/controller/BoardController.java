package board.mybatis.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import board.mybatis.mvc.dto.BoardCreateDTO;
import board.mybatis.mvc.dto.BoardDTO;
import board.mybatis.mvc.service.BoardService;
import board.mybatis.mvc.util.ManagementCookie;
import board.mybatis.mvc.util.PageRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

// Board Controller Class
@Log4j2
@Controller
@RequestMapping("/board/")
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
        return "/board/create";
    }

    // GET | Read Board
    @GetMapping("read/{bno}")
    public String getReadBoard(@PathVariable("bno") Long bno, Model model, PageRequestDTO pageRequestDTO,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("GET | Read Board Controller");
        if (managementCookie.createCookie(request, response, bno)) {
            log.info("Making Cookie");
            boardService.boardViewCount(bno);
        }
        BoardDTO list = boardService.readBoard(bno);
        model.addAttribute("list", list);
        return "/board/read";
    }

}
