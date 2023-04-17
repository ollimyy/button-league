package fi.ollimyy.buttonleague.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // home page is coming later, for now this just redirects to match-list
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/match-list";
    }
}
