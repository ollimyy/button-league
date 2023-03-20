package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.Team;
import fi.ollimyy.buttonleague.domain.TeamRepository;
import fi.ollimyy.buttonleague.model.TeamStats;
import fi.ollimyy.buttonleague.service.TeamStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamsController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamStatsService teamStatsService;

    //!!! TODO: this will give an error if team has not played any matches
    //TODO: table needs to be sorted and position numbers displayed
    @GetMapping("/league-table")
    public String showLeagueTable(Model model) {
        Iterable<Team> teams = teamRepository.findAll();
        List<TeamStats> teamsStats = new ArrayList<>();

        for(Team team : teams){
            teamsStats.add(teamStatsService.getStatsForTeam(team));
        }

        model.addAttribute("teamsStats", teamsStats);
        return "league-table";
    }
}
