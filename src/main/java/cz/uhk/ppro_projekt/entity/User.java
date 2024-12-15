package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
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
    @JoinTable(name = "user_favorite_team", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> favoriteTeams = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_favorite_player", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> favoritePlayers = new HashSet<>();
}