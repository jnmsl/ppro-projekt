package cz.uhk.ppro_projekt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String country;

    private String league;

    private Integer foundedYear;

    private String stadiumName;

    private String logoUrl;

    @OneToMany(mappedBy = "team")
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "homeTeam")
    private Set<Match> homeMatches = new HashSet<>();

    @OneToMany(mappedBy = "awayTeam")
    private Set<Match> awayMatches = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteTeams")
    private Set<User> followers = new HashSet<>();
}