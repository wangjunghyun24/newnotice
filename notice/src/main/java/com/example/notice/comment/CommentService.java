package com.example.notice.comment;


import com.example.notice.board.Board;
import com.example.notice.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service

public class CommentService {
    private final CommentRepository commentRepository;


    public void create(Board board, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setBoard(board);
        this.commentRepository.save(comment);
    }
}