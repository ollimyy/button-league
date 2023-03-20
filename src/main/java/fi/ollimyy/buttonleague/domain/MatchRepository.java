package fi.ollimyy.buttonleague.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query("SELECT m FROM Match m ORDER BY m.startDateTime ASC")
    List<Match> findAllMatchesSortedByStartDate();

    // NULL score means the match has not been played yet
    @Query("SELECT m FROM Match m WHERE ((m.homeTeam = :team OR m.awayTeam = :team) AND m.awayScore IS NOT NULL AND m.homeScore IS NOT NULL)")
    List<Match> findMatchesPlayedByTeam(@Param("team") Team team);

    @Query("SELECT m FROM Match m WHERE (m.homeTeam = :team AND m.homeScore > m.awayScore) OR (m.awayTeam = :team AND m.homeScore < m.awayScore)")
    List<Match> findMatchesWonByTeam(@Param("team") Team team);

    @Query("SELECT m FROM Match m WHERE (m.homeTeam = :team AND m.homeScore < m.awayScore) OR (m.awayTeam = :team AND m.homeScore > m.awayScore)")
    List<Match> findMatchesLostByTeam(@Param("team") Team team);

    // null = null will return false, so upcoming matches will be excluded
    @Query("SELECT m FROM Match m WHERE (m.homeTeam = :team OR m.awayTeam = :team) AND m.homeScore = m.awayScore")
    List<Match> findMatchesDrawnByTeam(@Param("team") Team team);

    @Query("SELECT SUM(CASE WHEN m.homeTeam = :team THEN m.homeScore ELSE m.awayScore END) FROM Match m WHERE ((m.homeTeam = :team OR m.awayTeam = :team) AND m.awayScore IS NOT NULL AND m.homeScore IS NOT NULL)")
    int findGoalsScoredByTeam(@Param("team") Team team);

    @Query("SELECT SUM(CASE WHEN m.homeTeam = :team THEN m.awayScore ELSE m.homeScore END) FROM Match m WHERE ((m.homeTeam = :team OR m.awayTeam = :team) AND m.awayScore IS NOT NULL AND m.homeScore IS NOT NULL)")
    int findGoalsConcededByTeam(@Param("team") Team team);


}
