package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.Match;
import fi.ollimyy.buttonleague.domain.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public Map<LocalDate, List<Match>> getMatchesGroupedByDate() {
        List<Match> matches = matchRepository.findAllMatchesSortedByStartDate();

        /*
        https://www.baeldung.com/java-groupingby-collector
        https://www.baeldung.com/java-8-streams

        First we create a stream of the matches list - matches.stream()
        Then we collect the elements of the stream into a Map, the keys are dates and values are matches
         */
        return matches.stream().collect(Collectors.groupingBy(match ->
                match.getStartDateTime().toLocalDate()));
    }
}
