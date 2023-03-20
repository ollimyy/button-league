package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.MatchRepository;
import fi.ollimyy.buttonleague.domain.Team;
import fi.ollimyy.buttonleague.model.TeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamStatsService {

    @Autowired
    MatchRepository matchRepository;

    public TeamStats getStatsForTeam(Team team) {
        TeamStats teamStats = new TeamStats();

        teamStats.setTeam(team);
        teamStats.setMatchesPlayed(matchRepository.findMatchesPlayedByTeam(team).size());
        teamStats.setLosses(matchRepository.findMatchesLostByTeam(team).size());

        int wins = matchRepository.findMatchesWonByTeam(team).size();
        int draws = matchRepository.findMatchesDrawnByTeam(team).size();
        teamStats.setWins(wins);
        teamStats.setDraws(draws);
        teamStats.setPoints(wins * 3 + draws); // win is 3 points in football, draw is 1 and loss is 0

        int goalsFor = matchRepository.findGoalsScoredByTeam(team);
        int goalsAgainst = matchRepository.findGoalsConcededByTeam(team);
        teamStats.setGoalsFor(goalsFor);
        teamStats.setGoalsAgainst(goalsAgainst);
        teamStats.setGoalDifference(goalsFor - goalsAgainst);

        return teamStats;
    }


}
