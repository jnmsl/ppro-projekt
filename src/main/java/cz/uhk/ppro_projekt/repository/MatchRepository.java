package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeTeamIdOrAwayTeamId(Long homeTeamId, Long awayTeamId);

    List<Match> findByMatchDateBetween(LocalDateTime start, LocalDateTime end);

    List<Match> findByCompetitionName(String competitionName);

    List<Match> findByStatus(String status);

    @Query("SELECT m FROM Match m WHERE m.matchDate > :now ORDER BY m.matchDate ASC")
    List<Match> findUpcomingMatches(@Param("now") LocalDateTime now);
}