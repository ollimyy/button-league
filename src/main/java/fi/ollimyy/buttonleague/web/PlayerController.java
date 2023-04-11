package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.Player;
import fi.ollimyy.buttonleague.domain.PlayerRepository;
import fi.ollimyy.buttonleague.domain.TeamRepository;
import fi.ollimyy.buttonleague.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    TeamRepository teamRepository;

    @PostMapping("/save-player")
    @Secured("ADMIN")
    public String savePlayer(Player player) {
        playerRepository.save(player);

        // redirect to the team the player is in after saving
        return "redirect:/team/" + player.getTeam().getId();
    }

    @GetMapping("/delete-player/{playerId}")
    @Secured("ADMIN")
    public String deletePlayer(@PathVariable("playerId") Long playerId, @RequestHeader String referer) {
        playerRepository.deleteById(playerId);
        //go back to the page where user clicked delete player
        return "redirect:" + referer;
    }

    @GetMapping("/edit-player/{playerId}")
    @Secured("ADMIN")
    public String editPlayer(@PathVariable("playerId") Long playerId, Model model, HttpSession session, @RequestHeader String referer) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);

        if(playerOptional.isEmpty()) {
            session.setAttribute("errorMessage", "Player not found.");
            //go back to the page where user clicked edit player
            return "redirect:" + referer;

        } else {
            Player player = playerOptional.get();
            List<Integer> numbersAvailableInCurrentTeam = playerService.getAvailablePlayerNumbersInTeam(player.getTeam());
            // add players current number to the list of numbers that can be selected for this player
            numbersAvailableInCurrentTeam.add(player.getNumber());
            // sort the list otherwise current number is last
            Collections.sort(numbersAvailableInCurrentTeam);

            model.addAttribute("player", player);
            model.addAttribute("availableNumbers", numbersAvailableInCurrentTeam);
            model.addAttribute("teams", teamRepository.findAll());

            return "edit-player";
        }


    }
}
