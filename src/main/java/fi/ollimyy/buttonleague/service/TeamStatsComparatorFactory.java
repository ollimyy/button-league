package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.MatchRepository;
import fi.ollimyy.buttonleague.model.TeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class TeamStatsComparatorFactory {

    @Autowired
    MatchRepository matchRepository;

    public Comparator<TeamStats> createTeamStatsComparator() {
        return (ts1, ts2) -> {

            // League table tiebreaker rules:
            //1. points
            if (ts1.getPoints() != ts2.getPoints()) {
                return Integer.compare(ts2.getPoints(), ts1.getPoints()); //returns negative if ts2 has more points -> ts2 placed before ts1
            }

            //2. goal difference
            if (ts1.getGoalDifference() != ts2.getGoalDifference()) {
                return Integer.compare(ts2.getGoalDifference(), ts1.getGoalDifference());
            }

            //3. goals scored
            if (ts1.getGoalsFor() != ts2.getGoalsFor()) {
                return Integer.compare(ts2.getGoalsFor(), ts1.getGoalsFor());
            }

            //4. wins in head-to-head matches
            int headToHeadWinDifference = matchRepository.findHeadToHeadWinDifference(ts2.getTeam(), ts1.getTeam());
            if (headToHeadWinDifference != 0) {
                return Integer.signum(headToHeadWinDifference); //signum to get only 1, 0, -1
            }

            //5. away goals in head-to-head matches
            return Integer.signum(matchRepository.findHeadToHeadAwayGoalDifference(ts2.getTeam(), ts1.getTeam()));
        };
    }
}

