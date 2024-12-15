package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserId(Long userId);

    List<Alert> findByMatchId(Long matchId);

    List<Alert> findByUserIdAndIsActiveTrue(Long userId);
}