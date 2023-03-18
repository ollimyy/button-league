package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MatchController {

    @Autowired
    MatchRepository repository;

    @GetMapping("/match-list")
    public String listAllMatches(Model model) {
        model.addAttribute("matches", repository.findAll());
        return "match-list";
    }
}
