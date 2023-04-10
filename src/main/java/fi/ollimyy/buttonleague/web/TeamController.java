package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.*;
import fi.ollimyy.buttonleague.model.TeamStats;
import fi.ollimyy.buttonleague.service.PlayerService;
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
import java.util.Optional;

@Controller
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamStatsService teamStatsService;

    @Autowired
    PlayerService playerService;

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
    public String showTeamPage(@PathVariable Long teamId, Model model, HttpSession session) {

        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            session.setAttribute("errorMessage", "Team not found.");
            return "redirect:/team-list";
        } else {
            Team team = teamOptional.get();


            // for showing the team's name and it's players
            model.addAttribute("team", team);
            model.addAttribute("players", playerRepository.findPlayersByTeamId(teamId));

            // for adding a new player to the team
            Player newPlayer = new Player();
            newPlayer.setTeam(team);
            model.addAttribute("newPlayer", newPlayer); //for adding new players to the team
            model.addAttribute("availableNumbers", playerService.getAvailablePlayerNumbersInTeam(team));

            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage"); // remove error message from session so when user refreshes page it won't show again

            return "team-page";
        }
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
    public String deleteTeam(@PathVariable("teamId") Long teamId, HttpSession session) {

        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            session.setAttribute("errorMessage", "Team not found.");
        } else {
            Team team = teamOptional.get();

            // Delete all players associated with the team
            List<Player> players = playerRepository.findPlayersByTeamId(teamId);
            playerRepository.deleteAll(players);

            // Delete all matches where the team is either the home team or away team
            List<Match> matches = matchRepository.findAllMatchesByTeam(team);
            matchRepository.deleteAll(matches);

            teamRepository.deleteById(teamId);
        }
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
