package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMatchId(Long matchId);

    List<Comment> findByUserId(Long userId);

    List<Comment> findByParentCommentId(Long parentCommentId);

    List<Comment> findByMatchIdOrderByCreatedAtDesc(Long matchId);
}