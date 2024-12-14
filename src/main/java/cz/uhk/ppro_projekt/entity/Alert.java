package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter
@Setter
public class Alert extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @Column(nullable = false)
    private String alertType;

    private boolean isActive = true;

    @Column(columnDefinition = "TEXT")
    private String conditionsJson;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}