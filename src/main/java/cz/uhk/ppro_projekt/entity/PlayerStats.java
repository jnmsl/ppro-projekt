package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "player_stats")
@Getter
@Setter
public class PlayerStats extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    private Integer goals;

    private Integer assists;

    private Integer minutesPlayed;

    @Column(columnDefinition = "TEXT")
    private String detailedStatsJson;
}