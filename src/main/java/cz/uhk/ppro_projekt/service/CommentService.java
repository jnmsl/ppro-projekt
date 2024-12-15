package cz.uhk.ppro_projekt.service;

import cz.uhk.ppro_projekt.entity.Comment;
import cz.uhk.ppro_projekt.entity.Match;
import cz.uhk.ppro_projekt.entity.User;
import cz.uhk.ppro_projekt.repository.CommentRepository;
import cz.uhk.ppro_projekt.repository.MatchRepository;
import cz.uhk.ppro_projekt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public Comment createComment(Long userId, Long matchId, String content, Long parentCommentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setMatch(match);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        return commentRepository.save(comment);
    }

    public List<Comment> getMatchComments(Long matchId) {
        return commentRepository.findByMatchIdOrderByCreatedAtDesc(matchId);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    public List<Comment> getCommentReplies(Long parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }
}