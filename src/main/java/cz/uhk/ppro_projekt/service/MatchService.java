package cz.uhk.ppro_projekt.service;

import cz.uhk.ppro_projekt.entity.Match;
import cz.uhk.ppro_projekt.entity.Team;
import cz.uhk.ppro_projekt.entity.PlayerStats;
import cz.uhk.ppro_projekt.repository.MatchRepository;
import cz.uhk.ppro_projekt.repository.PlayerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {
    private final MatchRepository matchRepository;
    private final PlayerStatsRepository playerStatsRepository;

    public List<Match> getUpcomingMatches() {
        return matchRepository.findUpcomingMatches(LocalDateTime.now());
    }

    public List<Match> getMatchesByTeam(Long teamId) {
        return matchRepository.findByHomeTeamIdOrAwayTeamId(teamId, teamId);
    }

    public List<Match> getMatchesByDateRange(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findByMatchDateBetween(start, end);
    }

    public Match getMatchById(Long matchId) {
        return matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public Match updateMatchScore(Long matchId, Integer homeScore, Integer awayScore) {
        Match match = getMatchById(matchId);
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        match.setStatus("COMPLETED");
        return matchRepository.save(match);
    }

    public List<PlayerStats> getMatchStatistics(Long matchId) {
        return playerStatsRepository.findByMatchId(matchId);
    }

    public List<PlayerStats> getMatchScorers(Long matchId) {
        return playerStatsRepository.findScorersInMatch(matchId);
    }
}