package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matches")
@Getter
@Setter
public class Match extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    private String competitionName;

    @Column(nullable = false)
    private LocalDateTime matchDate;

    private String venue;

    private Integer homeScore;

    private Integer awayScore;

    @Column(nullable = false)
    private String status;

    private String round;

    @Column(columnDefinition = "TEXT")
    private String matchStatsJson;

    @OneToMany(mappedBy = "match")
    private Set<PlayerStats> playerStats = new HashSet<>();

    @OneToMany(mappedBy = "match")
    private Set<Prediction> predictions = new HashSet<>();

    @OneToMany(mappedBy = "match")
    private Set<Comment> comments = new HashSet<>();
}