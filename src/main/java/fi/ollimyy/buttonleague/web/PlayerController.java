package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.Player;
import fi.ollimyy.buttonleague.domain.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/save-player")
    @Secured("ADMIN")
    public String savePlayer(Player player) {
        playerRepository.save(player);

        return "redirect:/team/" + player.getTeam().getId();
    }

    @GetMapping("/delete-player/{playerId}")
    @Secured("ADMIN")
    public String deletePlayer(@PathVariable("playerId") Long playerId, @RequestHeader String referer) {
        playerRepository.deleteById(playerId);

        return "redirect:" + referer;
    }
}
