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

    /* https://www.postgresql.org/docs/current/functions-conditional.html#FUNCTIONS-COALESCE-NVL-IFNULL
    The sum returns null if the team has not played any matches yet. The awayScore and homeScore are null when the team has upcoming matches, they have not played yet.
    If the team has no matches in the database the sum has an empty set and will return null. In these cases using coalesce we return 0 goals scored or conceded.
    */
    @Query("SELECT COALESCE(SUM(CASE WHEN m.homeTeam = :team THEN m.homeScore ELSE m.awayScore END), 0) FROM Match m WHERE m.homeTeam = :team OR m.awayTeam = :team")
    int findGoalsScoredByTeam(@Param("team") Team team);

    @Query("SELECT COALESCE(SUM(CASE WHEN m.homeTeam = :team THEN m.awayScore ELSE m.homeScore END), 0) FROM Match m WHERE m.homeTeam = :team OR m.awayTeam = :team")
    int findGoalsConcededByTeam(@Param("team") Team team);


}
