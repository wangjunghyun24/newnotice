package com.example.notice.comment;

import com.example.notice.board.Board;
import com.example.notice.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Controller

public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("comment/create/{id}")
    public String createcomment(Model model, @PathVariable("id") Integer id,@Valid CommentForm commentForm, BindingResult bindingResult) {
        Board board = this.boardService.getBoard(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board_detail";
        }

        this.commentService.create(board, commentForm.getContent());

        return String.format("redirect:/board/detail/%s", id);
    }
}

