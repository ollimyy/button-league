package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeamsController {

    @Autowired
    TeamRepository repository;

    @GetMapping("/team-list")
    public String listAllTeams(Model model){
        model.addAttribute("teams", repository.findAll());
        return "team-list";
    }
}
