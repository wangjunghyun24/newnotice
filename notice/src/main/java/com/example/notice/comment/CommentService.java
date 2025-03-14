package com.example.notice.comment;


import com.example.notice.board.Board;
import com.example.notice.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.notice.user.SiteUser;
import java.util.Optional;
import com.example.notice.board.DataNotFoundException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service

public class CommentService {
    private final CommentRepository commentRepository;


    public void create(Board board, String content,SiteUser user) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setBoard(board);
        comment.setAuthor(user);
        this.commentRepository.save(comment);
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }
}