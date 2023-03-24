package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.PlayerRepository;
import fi.ollimyy.buttonleague.domain.Team;
import fi.ollimyy.buttonleague.domain.TeamRepository;
import fi.ollimyy.buttonleague.model.TeamStats;
import fi.ollimyy.buttonleague.service.TeamStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

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

        teamsStats = teamStatsService.sortTeamStatsForLeagueTable(teamsStats);

        model.addAttribute("teamsStats", teamsStats);
        return "league-table";
    }

    @GetMapping("/team/{teamId}/player-list")
    public String listAllPlayersByTeam(@PathVariable Long teamId, Model model) {
        model.addAttribute("team", teamRepository.findById(teamId).get()); //TODO: handle team not found
        model.addAttribute("players", playerRepository.findPlayersByTeamId(teamId));

        return "player-list";
    }
}
