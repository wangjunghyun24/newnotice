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
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/board/list")
    public String list(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
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
    @GetMapping("/board/create")
    public String boardCreate(BoardForm boardForm) {
        return "board_form";
    }
    @PostMapping("/board/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board_form";
        }

        // TODO 질문을 저장한다.
        this.boardService.create(boardForm.getSubject(), boardForm.getContent());
        return "redirect:/board/list";
    }
    @GetMapping("/board/modify/{id}")
    public String boardModify(BoardForm boardForm, @PathVariable("id") Integer id, Model model) {
        Board board = this.boardService.getBoard(id);
        boardForm.setId(board.getId());
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());
        model.addAttribute("board", board);
        model.addAttribute("boardForm", boardForm);
        return "reboard_form";
    }
    @PostMapping("/board/modify/{id}")
    public String boardModify(@Valid BoardForm boardForm, BindingResult bindingResult,
                                 @PathVariable ("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "reboard_form";
        }
        Board board = this.boardService.getBoard(id);
        this.boardService.modify(board, boardForm.getSubject(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", id);
    }
    @GetMapping("board/delete/{id}")
    public String questionDelete( @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);

        this.boardService.delete(board);
        return "redirect:/";
    }
}
