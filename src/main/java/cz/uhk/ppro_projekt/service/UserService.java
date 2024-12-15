package cz.uhk.ppro_projekt.service;

import cz.uhk.ppro_projekt.entity.User;
import cz.uhk.ppro_projekt.entity.Team;
import cz.uhk.ppro_projekt.entity.Player;
import cz.uhk.ppro_projekt.repository.UserRepository;
import cz.uhk.ppro_projekt.repository.TeamRepository;
import cz.uhk.ppro_projekt.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user, String password) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRegistrationDate(LocalDateTime.now());
        user.setSubscriptionType("free");
        user.setActive(true);

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public void addFavoriteTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));

        user.getFavoriteTeams().add(team);
        userRepository.save(user);
    }

    public void addFavoritePlayer(Long userId, Long playerId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));

        user.getFavoritePlayers().add(player);
        userRepository.save(user);
    }

    public Set<Team> getFavoriteTeams(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteTeams();
    }

    public Set<Player> getFavoritePlayers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoritePlayers();
    }
}