package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.PlayerRepository;
import fi.ollimyy.buttonleague.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public List<Integer> getAvailablePlayerNumbersInTeam(Team team) {
        List<Integer> used = playerRepository.findNumbersUsedInTeam(team);
        List<Integer> available = new ArrayList<>();
        // numbers 1-99 can be selected for player
        for (int i = 1; i < 100; i++) {
            if (!used.contains(i)) {
                available.add(i);
            }
        }

            return available;
    }
}
