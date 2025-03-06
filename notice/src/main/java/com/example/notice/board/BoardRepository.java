package com.example.notice.board;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepository extends JpaRepository<Board, Integer>{
    Board findBySubject(String subject);
    Board findBySubjectAndContent(String subject, String content);
    List<Board> findBySubjectLike(String subject);
    Page<Board> findAll(Pageable pageable);
}
