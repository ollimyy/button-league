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
        List<Match> matches = matchRepository.findAllMatchesSortedByStartDate();
        // with a LinkedHashMap the match groups stay sorted by date
        Map<LocalDate, List<Match>> matchesGroupedByDate = new LinkedHashMap<>();

        for (Match match : matches) {

            // matches have startDateTime, we need Date for grouping
            LocalDate startDate = null;

            // if the match has a start date convert it to LocalDate
            if (match.getStartDateTime() != null) {
                startDate = match.getStartDateTime().toLocalDate();
            }

            // if there is no list for this start date create one
            if (!matchesGroupedByDate.containsKey(startDate)) {
                matchesGroupedByDate.put(startDate, new ArrayList<>());
            }

            // add the match to a list matching its start date
            matchesGroupedByDate.get(startDate).add(match);
        }
        return matchesGroupedByDate;
    }
}
