package com.example.notice.comment;

import com.example.notice.board.Board;
import com.example.notice.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;


import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.notice.user.SiteUser;
import com.example.notice.user.UserService;
@RequiredArgsConstructor
@Controller

public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("comment/create/{id}")
    public String createcomment(Model model, @PathVariable("id") Integer id,@Valid CommentForm commentForm, BindingResult bindingResult,Principal principal) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board_detail";
        }

        this.commentService.create(board, commentForm.getContent(),siteUser);

        return String.format("redirect:/board/detail/%s", id);
    }
}

