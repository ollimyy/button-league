package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.Match;
import fi.ollimyy.buttonleague.domain.MatchRepository;
import fi.ollimyy.buttonleague.domain.Player;
import fi.ollimyy.buttonleague.domain.PlayerRepository;
import fi.ollimyy.buttonleague.domain.Team;
import fi.ollimyy.buttonleague.domain.TeamRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDataInitializer {

    /**
     * This class is used to create test data for the application.
     * It creates four teams and six players for each team and some matches.
     * The test data is created only if the database is empty.
     *
     * Test data was created with an LLM.
     */

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    public void initializeTeams() {
        if (teamRepository.count() == 0) {
            try {
                // Create four teams
                Team panthers = new Team("Pasila Panthers");
                Team kings = new Team("Kumpula Kings");
                Team titans = new Team("Toukola Titans");
                Team vikings = new Team("Vallila Vikings");

                // Save the teams to the database
                teamRepository.save(panthers);
                teamRepository.save(kings);
                teamRepository.save(titans);
                teamRepository.save(vikings);

                // Create players for each team
                createPlayersForTeam(panthers);
                createPlayersForTeam(kings);
                createPlayersForTeam(titans);
                createPlayersForTeam(vikings);

                System.out.println("Test teams and players created successfully");
            } catch (Exception e) {
                System.err.println("Error creating test data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void createPlayersForTeam(Team team) {
        if (team.getName().equals("Pasila Panthers")) {
            playerRepository.save(new Player("Leo", "Metsä", 10, team));
            playerRepository.save(new Player("Risto", "Donaldo", 7, team));
            playerRepository.save(new Player("Kyylian", "Bmoppé", 9, team));
            playerRepository.save(new Player("Robert", "Levanperä", 11, team));
            playerRepository.save(new Player("Kevin", "DeBrouwer", 17, team));
            playerRepository.save(new Player("Luka", "Modernich", 8, team));
        } else if (team.getName().equals("Kumpula Kings")) {
            playerRepository.save(new Player("Erling", "Haalandö", 9, team));
            playerRepository.save(new Player("Vini", "Juniori", 7, team));
            playerRepository.save(new Player("Marko", "Salako", 11, team));
            playerRepository.save(new Player("Neymari", "Juniori", 10, team));
            playerRepository.save(new Player("Toni", "Kroosinkilpi", 8, team));
            playerRepository.save(new Player("Jude", "Pellinki", 5, team));
        } else if (team.getName().equals("Toukola Titans")) {
            playerRepository.save(new Player("Virgil", "Van Dyykkari", 4, team));
            playerRepository.save(new Player("Kermi", "Mbabu", 6, team));
            playerRepository.save(new Player("Anssu", "Faatti", 10, team));
            playerRepository.save(new Player("Manuel", "Neuvokas", 1, team));
            playerRepository.save(new Player("Rodri", "Koivusaari", 16, team));
            playerRepository.save(new Player("Harry", "Kaino", 9, team));
        } else if (team.getName().equals("Vallila Vikings")) {
            playerRepository.save(new Player("Jamal", "Musiaali", 10, team));
            playerRepository.save(new Player("Pyry", "Harland", 9, team));
            playerRepository.save(new Player("Joona", "Kimmikki", 6, team));
            playerRepository.save(new Player("Thibaut", "Kourtois", 1, team));
            playerRepository.save(new Player("Petteri", "Mahti", 8, team));
            playerRepository.save(new Player("Bruno", "Linnanmäki", 18, team));
        }
    }

    public void initializeMatches() {
        if (matchRepository.count() == 0) {
            try {
                // Get all teams
                List<Team> teams = new ArrayList<>();
                teamRepository.findAll().forEach(teams::add);

                Team panthers = null, kings = null, titans = null, vikings = null;
                for (Team team : teams) {
                    switch (team.getName()) {
                        case "Pasila Panthers" -> panthers = team;
                        case "Kumpula Kings" -> kings = team;
                        case "Toukola Titans" -> titans = team;
                        case "Vallila Vikings" -> vikings = team;
                    }
                }

                // Panthers vs Kings matches

                Match match1 = new Match(LocalDate.now().minusDays(28), LocalTime.of(18, 30),
                        panthers, kings, 2, 1, new HashSet<>());
                matchRepository.save(match1);

                Match match2 = new Match(LocalDate.now().minusDays(14), LocalTime.of(18, 30),
                        kings, panthers, 3, 2, new HashSet<>());
                matchRepository.save(match2);

                // Titans vs Vikings matches
                Match match3 = new Match(LocalDate.now().minusDays(28), LocalTime.of(20, 0),
                        titans, vikings, 2, 2, new HashSet<>());
                matchRepository.save(match3);

                Match match4 = new Match(LocalDate.now().minusDays(14), LocalTime.of(20, 0),
                        vikings, titans, 4, 4, new HashSet<>());
                matchRepository.save(match4);

                // Panthers vs Titans
                Match match5 = new Match(LocalDate.now().minusDays(21), LocalTime.of(18, 30),
                        panthers, titans, 3, 0, new HashSet<>());
                matchRepository.save(match5);

                // Kings vs Vikings
                Match match6 = new Match(LocalDate.now().minusDays(21), LocalTime.of(20, 0),
                        kings, vikings, 1, 1, new HashSet<>());
                matchRepository.save(match6);

                // Kings vs Titans
                Match match7 = new Match(LocalDate.now().minusDays(7), LocalTime.of(18, 30),
                        kings, titans, 2, 0, new HashSet<>());
                matchRepository.save(match7);

                // Panthers vs Vikings
                Match match8 = new Match(LocalDate.now().minusDays(7), LocalTime.of(20, 0),
                        vikings, panthers, 1, 0, new HashSet<>());
                matchRepository.save(match8);

                // Upcoming matches (no scores)
                Match match9 = new Match(LocalDate.now().plusDays(7), LocalTime.of(18, 30),
                        titans, kings, null, null, new HashSet<>());
                matchRepository.save(match9);

                Match match10 = new Match(LocalDate.now().plusDays(7), LocalTime.of(20, 0),
                        panthers, vikings, null, null, new HashSet<>());
                matchRepository.save(match10);

                Match match11 = new Match(LocalDate.now().plusDays(14), LocalTime.of(19, 0),
                        titans, panthers, null, null, new HashSet<>());
                matchRepository.save(match11);

                Match match12 = new Match(LocalDate.now().plusDays(14), LocalTime.of(21, 0),
                        vikings, kings, null, null, new HashSet<>());
                matchRepository.save(match12);

                System.out.println("Test matches created successfully");

            } catch (Exception e) {
                System.err.println("Error creating test matches: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}