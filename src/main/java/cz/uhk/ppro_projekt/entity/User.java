package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // 'user' is a reserved keyword in some DBs
@Getter
@Setter
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String fullName;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private String subscriptionType = "free";

    private boolean isActive = true;

    @Column(columnDefinition = "TEXT")
    private String preferencesJson;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_team",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> favoriteTeams = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorite_player",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> favoritePlayers = new HashSet<>();
}