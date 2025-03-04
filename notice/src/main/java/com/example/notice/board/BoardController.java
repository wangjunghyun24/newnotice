package com.example.notice.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/board/list")
    public String list(Model model) {
        List<Board> questionList = this.boardService.getList();
        model.addAttribute("questionList", questionList);
        return "board_list";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/board/list";
    }
    @GetMapping(value = "/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }
}
