package fi.ollimyy.buttonleague.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime startDateTime;

    @ManyToOne
    @JoinColumn (name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn (name = "away_team_id")
    @Formula("(select team_id from match_team where match_id = id and is_home_team = true)")
    private Team awayTeam;

    private Integer homeScore;

    private Integer awayScore;

    @ManyToMany
    @JoinTable(
            name = "Match_Team",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    //constructors
    public Match() {
    }

    public Match(Long id, Team homeTeam, Team awayTeam, Integer homeScore, Integer awayScore) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long matchId) {
        this.id = matchId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeTeamScore) {
        this.homeScore = homeTeamScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayTeamScore) {
        this.awayScore = awayTeamScore;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + id +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeTeamScore=" + homeScore +
                ", awayTeamScore=" + awayScore +
                '}';
    }
}




