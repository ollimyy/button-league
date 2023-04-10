package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.PlayerRepository;
import fi.ollimyy.buttonleague.domain.Team;
import fi.ollimyy.buttonleague.domain.TeamRepository;
import fi.ollimyy.buttonleague.model.TeamStats;
import fi.ollimyy.buttonleague.service.TeamStatsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/team/{teamId}")
    public String listAllPlayersByTeam(@PathVariable Long teamId, Model model, HttpSession session) {
        model.addAttribute("team", teamRepository.findById(teamId).get()); //TODO: handle team not found
        model.addAttribute("players", playerRepository.findPlayersByTeamId(teamId));
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("errorMessage");

        return "team-page";
    }

    @GetMapping("/team-list")
    public String listAllTeams(Model model, HttpSession session) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("newTeam", new Team());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("errorMessage");

        return "team-list";
    }

    @PostMapping("/add-team")
    @Secured("ADMIN")
    public String addNewTeam(@ModelAttribute("newTeam")Team team, @RequestParam("redirectToPlayers") boolean redirectToPlayers, HttpSession session) {

        try {
            teamRepository.save(team);
        } catch (DataIntegrityViolationException e) {
            session.setAttribute("errorMessage", "Team name already exists.");
            return "redirect:/team-list";
        }
        if(redirectToPlayers) {
            return "redirect:/team/" + team.getId();
        } else {
            return "redirect:/team-list";
        }
    }


    @GetMapping("/delete-team/{teamId}")
    @Secured("ADMIN")
    public String deleteTeam(@PathVariable("teamId") Long teamId) {
        teamRepository.deleteById(teamId);

        return "redirect:/team-list";
    }

    @GetMapping("/save-team")
    @Secured("ADMIN")
    public String saveTeam(Team team, HttpSession session) {

       try {
           teamRepository.save(team);
       } catch (DataIntegrityViolationException e) {
           session.setAttribute("errorMessage", "Team name already exists.");
           return "redirect:/team/" + team.getId();
       }
        return "redirect:/team/" + team.getId();
    }


}
