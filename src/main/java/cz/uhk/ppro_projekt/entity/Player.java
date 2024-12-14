package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate birthDate;

    private String nationality;

    private String position;

    private String jerseyNumber;

    private LocalDateTime joinedTeam;

    @OneToMany(mappedBy = "player")
    private Set<PlayerStats> statistics = new HashSet<>();

    @ManyToMany(mappedBy = "favoritePlayers")
    private Set<User> followers = new HashSet<>();
}