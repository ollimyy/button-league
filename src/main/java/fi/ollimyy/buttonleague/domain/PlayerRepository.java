package fi.ollimyy.buttonleague.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.team.id = :teamId")
    List<Player> findPlayersByTeamId(@Param("teamId") Long team);

    @Query("SELECT p.number FROM Player p WHERE p.team = :team")
    List<Integer> findNumbersUsedInTeam(@Param("team") Team team);


}
