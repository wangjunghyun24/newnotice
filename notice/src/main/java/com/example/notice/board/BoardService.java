package com.example.notice.board;

import java.util.List;
import java.util.Optional;
import com.example.notice.board.DataNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getList() {
        return this.boardRepository.findAll();
    }

    public Board getBoard(Integer id) {
        Optional<Board> question = this.boardRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }
    public void create(String subject, String content) {
        Board b = new Board();
        b.setSubject(subject);
        b.setContent(content);
        b.setCreateDate(LocalDateTime.now());
        this.boardRepository.save(b);
    }

}
