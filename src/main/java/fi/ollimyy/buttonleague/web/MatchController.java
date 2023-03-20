package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.Match;
import fi.ollimyy.buttonleague.domain.MatchRepository;
import fi.ollimyy.buttonleague.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class MatchController {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchService matchService;

    @GetMapping("/match-list")
    public String listAllMatchesByDate(Model model) {
        Map<LocalDate, List<Match>> matchesByDate = matchService.getMatchesGroupedByDate();
        model.addAttribute("matchesByDate", matchesByDate);
        return "match-list";
    }
}
