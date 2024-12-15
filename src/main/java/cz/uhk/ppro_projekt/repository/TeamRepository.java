package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    List<Team> findByCountry(String country);

    List<Team> findByLeague(String league);
}
