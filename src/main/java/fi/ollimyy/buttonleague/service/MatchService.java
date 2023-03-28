package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.Match;
import fi.ollimyy.buttonleague.domain.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public Map<LocalDate, List<Match>> getMatchesGroupedByDate() {
        List<Match> matches = matchRepository.findAllMatchesSortedByStartDateAndTime();
        // with a LinkedHashMap the match groups stay sorted by date
        Map<LocalDate, List<Match>> matchesGroupedByDate = new LinkedHashMap<>();

        for (Match match : matches) {
            LocalDate date = match.getDate();

            // if no matches list for this date create one
            if(!matchesGroupedByDate.containsKey(date)) {
                matchesGroupedByDate.put(date, new ArrayList<>());
            }

            // add the match to the list matching its date
            matchesGroupedByDate.get(date).add(match);
        }

        return matchesGroupedByDate;
    }
}
