package board.mybatis.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import board.mybatis.mvc.util.cookie.ManagementCookie;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

/**
 * {@code BoardController}는 게시판과 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@Controller
@RequestMapping("spring/board/")
@Tag(name = "Board API", description = "게시판과 관련된 모든 API")
public class BoardController {

    private final BoardService boardService;
    private final ManagementCookie managementCookie;

    /**
     * 생성자를 통한 의존성 주입
     *
     * @param boardService     게시글 서비스
     * @param managementCookie 쿠키 관리 유틸리티
     */
    @Autowired
    public BoardController(final BoardService boardService, final ManagementCookie managementCookie) {
        log.info("Inject BoardService");
        this.boardService = boardService;
        this.managementCookie = managementCookie;
    }

    // GET | Create Board
    @GetMapping("create")
    @Operation(summary = "게시물 생성 페이지", description = "게시물 생성 페이지를 조회하는 API")
    public String getCreateBoard() {
        log.info("GET | Create Board Controller");
        return "spring/board/create";
    }

    // GET | Read Board
    @GetMapping("read/{bno}")
    @Operation(summary = "게시물 조회", description = "특정 게시물을 조회하는 API")
    public String getReadBoard(@Parameter(description = "게시물 번호", required = true) @PathVariable("bno") final Long bno,
            final Model model, final PageRequestDTO pageRequestDTO,
            HttpServletRequest request, HttpServletResponse response) {
        log.info("GET | Read Board Controller");
        if (managementCookie.createCookie(request, response, bno)) {
            log.info("Making Cookie");
            boardService.countViewBoard(bno);
        }
        BoardDTO list = boardService.readBoard(bno);
        model.addAttribute("list", list);
        return "spring/board/read";
    }

    // GET | Update Board
    @GetMapping("update/{bno}")
    @Operation(summary = "게시물 수정 페이지", description = "게시물 수정 페이지를 조회하는 API")
    public String getUpdateBoard(
            @Parameter(description = "수정할 게시물 번호", required = true) @PathVariable("bno") final long bno, Model model,
            final PageRequestDTO pageRequestDTO) {
        log.info("GET | Update Board Controller");
        BoardDTO list = boardService.readBoard(bno);
        model.addAttribute("list", list);
        return "spring/board/update";
    }

    // GET | List Board
    @GetMapping("list")
    @Operation(summary = "게시물 리스트", description = "게시물 목록을 조회하는 API")
    public String getListBoard(final PageRequestDTO pageRequestDTO, final Model model) {
        log.info("GET | List Board Controller");
        PageResponseDTO<BoardListDTO> list = boardService.listBoard(pageRequestDTO);
        model.addAttribute("list", list);
        return "spring/board/list";
    }

    // POST | Create Board
    @PostMapping("create")
    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성하는 API")
    public String postCreateBoard(@Valid final BoardCreateDTO boardCreateDTO,
            final RedirectAttributes redirectAttributes) {
        log.info("POST | Create Board Controller");
        log.info("boardCreateDTO: " + boardCreateDTO);
        Long createBoard = boardService.createBoard(boardCreateDTO);
        redirectAttributes.addFlashAttribute("message", "게시물 생성 완료.");
        return "redirect:/spring/board/list";
    }

    // POST | Update Board
    @PostMapping("update")
    @Operation(summary = "게시물 수정", description = "게시물의 내용을 수정하는 API")
    public String postUpdateBoard(@Valid final BoardUpdateDTO boardUpdateDTO,
            final RedirectAttributes redirectAttributes) {
        log.info("POST | Update Board Contoller");
        Long updateBoard = boardService.updateBoard(boardUpdateDTO);
        redirectAttributes.addFlashAttribute("message", "게시물 업데이트 완료.");
        return "redirect:/spring/board/read/" + boardUpdateDTO.getBno();
    }

    // POST | Delete Board
    @PostMapping("delete/{bno}")
    @Operation(summary = "게시물 삭제", description = "특정 게시물을 삭제하는 API")
    public String postDeleteBoard(
            @Parameter(description = "삭제할 게시물 번호", required = true) @PathVariable("bno") final Long bno,
            final RedirectAttributes redirectAttributes) {
        log.info("POST | Delete Board Controller");
        Long deleteBoard = boardService.deleteBoard(bno);
        redirectAttributes.addFlashAttribute("message", "게시물 삭제 완료.");
        return "redirect:/spring/board/list";
    }
}