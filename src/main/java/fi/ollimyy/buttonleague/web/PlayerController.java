package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.Player;
import fi.ollimyy.buttonleague.domain.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/save-player")
    public String savePlayer(Player player) {
        playerRepository.save(player);

        return "redirect:/team/" + player.getTeam().getId();
    }
}
