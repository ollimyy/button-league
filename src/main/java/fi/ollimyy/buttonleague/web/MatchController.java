package fi.ollimyy.buttonleague.web;

import fi.ollimyy.buttonleague.domain.Match;
import fi.ollimyy.buttonleague.domain.MatchRepository;
import fi.ollimyy.buttonleague.domain.TeamRepository;
import fi.ollimyy.buttonleague.service.MatchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MatchController {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MatchService matchService;

    @GetMapping("/match-list")
    public String listAllMatchesByDate(Model model) {
        Map<LocalDate, List<Match>> matchesByDate = matchService.getMatchesGroupedByDate();
        model.addAttribute("matchesByDate", matchesByDate);
        return "match-list";
    }

    @GetMapping("/add-match")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addMatch(Model model, HttpSession session) {
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("errorMessage"); // remove error message from session so it is only shown once
        model.addAttribute("match", new Match());
        model.addAttribute("teams", teamRepository.findAll());

        return "match-form";
    }

    @GetMapping("/edit-match/{matchId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editMatch(@PathVariable("matchId") Long matchId, Model model, HttpSession session) {
        Optional<Match> matchOptional = matchRepository.findById(matchId);

        if(matchOptional.isEmpty()) {
            session.setAttribute("errorMessage", "Match not found.");
            return "redirect:/match-list";
        } else {
            Match match = matchOptional.get();

            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage"); // remove error message from session so it is only shown once
            model.addAttribute("match", match);
            model.addAttribute("teams", teamRepository.findAll());

            return "match-form";
        }
    }

    @PostMapping("/save-match")
    public String saveMatch(Match match, HttpSession session) {

        try {
            matchRepository.save(match);
        } catch (TransactionSystemException e){
            session.setAttribute("errorMessage", "If the match is upcoming leave both scores empty. " +
                    "If the match has been played mark both scores.");

            Optional<Match> matchOptional = matchRepository.findById(match.getId());

            if(matchOptional.isEmpty()) {
                return "redirect:/add-match";
            } else {
                return "redirect:edit-match/" + matchOptional.get().getId();
            }
        }
        return "redirect:/match-list";
    }

    @GetMapping("/delete-match/{matchId}")
    public String deleteMatch(@PathVariable("matchId") Long matchId, HttpSession session) {
        Optional<Match> matchOptional = matchRepository.findById(matchId);

        if(matchOptional.isEmpty()) {
            session.setAttribute("errorMessage", "Match not found.");
            return "redirect:/match-list";
        } else {
            Match match = matchOptional.get();

            matchRepository.delete(match);
            return "redirect:/match-list";
        }
    }
}
