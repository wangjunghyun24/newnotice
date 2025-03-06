package com.example.notice.board;

import java.util.List;
import java.util.Optional;
import com.example.notice.board.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }
    public void create(String subject, String content) {
        Board b = new Board();
        b.setSubject(subject);
        b.setContent(content);
        b.setModifyDate(LocalDateTime.now());
        this.boardRepository.save(b);
    }

    public Page<Board> getList(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.boardRepository.findAll(pageable);
    }
    @Transactional
    public void modify(Board board, String subject, String content) {
        board.setSubject(subject);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());
        this.boardRepository.save(board);
    }
    public void delete(Board board) {
        this.boardRepository.delete(board);
    }

}
