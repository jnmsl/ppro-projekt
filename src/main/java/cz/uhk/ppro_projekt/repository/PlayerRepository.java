package cz.uhk.ppro_projekt.repository;

import cz.uhk.ppro_projekt.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(Long teamId);

    List<Player> findByNationality(String nationality);

    List<Player> findByPosition(String position);

    @Query("SELECT p FROM Player p WHERE p.firstName LIKE %:name% OR p.lastName LIKE %:name%")
    List<Player> findByNameContaining(@Param("name") String name);
}