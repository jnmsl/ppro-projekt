package cz.uhk.ppro_projekt.service;

import cz.uhk.ppro_projekt.entity.Prediction;
import cz.uhk.ppro_projekt.entity.Match;
import cz.uhk.ppro_projekt.entity.User;
import cz.uhk.ppro_projekt.repository.PredictionRepository;
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
public class PredictionService {
    private final PredictionRepository predictionRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public Prediction createPrediction(Long userId, Long matchId, Integer homeScore, Integer awayScore) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));

        if (match.getMatchDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot predict for past matches");
        }

        Prediction prediction = new Prediction();
        prediction.setUser(user);
        prediction.setMatch(match);
        prediction.setPredictedHomeScore(homeScore);
        prediction.setPredictedAwayScore(awayScore);
        prediction.setCreatedAt(LocalDateTime.now());

        return predictionRepository.save(prediction);
    }

    public List<Prediction> getUserPredictions(Long userId) {
        return predictionRepository.findByUserId(userId);
    }

    public List<Prediction> getMatchPredictions(Long matchId) {
        return predictionRepository.findByMatchId(matchId);
    }
}