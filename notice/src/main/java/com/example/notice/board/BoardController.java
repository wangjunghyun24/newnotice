package com.example.notice.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;
import com.example.notice.comment.CommentForm;

import java.security.Principal;
import com.example.notice.user.SiteUser;
import com.example.notice.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;


    @GetMapping("/board/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Board> paging = this.boardService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board_list";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/board/list";
    }


    @GetMapping(value = "/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id,CommentForm commentForm) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/create")
    public String boardCreate(BoardForm boardForm) {
        return "board_form";
    }

    @PostMapping("/board/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }

        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.create(boardForm.getSubject(), boardForm.getContent(),siteUser);
        return "redirect:/board/list";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/modify/{id}")
    public String boardModify(BoardForm boardForm, @PathVariable("id") Integer id, Model model, Principal principal) {
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        boardForm.setId(board.getId());
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());
        model.addAttribute("board", board);
        model.addAttribute("boardForm", boardForm);
        return "board_form";
    }



    @PostMapping("/board/modify/{id}")
    public String boardModify(@Valid BoardForm boardForm,
                              Principal principal, BindingResult bindingResult,
                                 @PathVariable ("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }
        Board board = this.boardService.getBoard(id);
        if (!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modify(board, boardForm.getSubject(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", id);
    }
    @GetMapping("board/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        if (!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.boardService.delete(board);
        return "redirect:/";
    }
}
