package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByUserId(Long userId);

    List<Prediction> findByMatchId(Long matchId);

    Optional<Prediction> findByUserIdAndMatchId(Long userId, Long matchId);
}