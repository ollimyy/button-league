package fi.ollimyy.buttonleague.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query("SELECT m FROM Match m ORDER BY m.startDateTime ASC")
    List<Match> findAllMatchesSortedByStartDate();
}
