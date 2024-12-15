package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
    List<PlayerStats> findByPlayerId(Long playerId);

    List<PlayerStats> findByMatchId(Long matchId);

    @Query("SELECT ps FROM PlayerStats ps WHERE ps.match.id = :matchId AND ps.goals > 0")
    List<PlayerStats> findScorersInMatch(@Param("matchId") Long matchId);
}