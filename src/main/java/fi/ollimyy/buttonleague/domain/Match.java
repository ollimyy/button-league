package fi.ollimyy.buttonleague.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // null date is used to indicate that it is an upcoming match that has not been scheduled yet
    // https://stackoverflow.com/questions/38693971/input-type-date-thymeleaf
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    // null time can be used if the match doesn't (yet) have a specified start time
    private LocalTime time;

    @ManyToOne
    @JoinColumn (name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn (name = "away_team_id", nullable = false)
    private Team awayTeam;

    // null score is used to indicate that the match has not started or that the score has not been recorded
    private Integer homeScore;

    private Integer awayScore;

    @ManyToMany
    @JoinTable(
            name = "Match_Team",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    // if date is null time must be null
    @AssertTrue
    private boolean isDateAndTimeValid() {
        return date != null || time == null;
    }

    // match must have a date when it was played before the score can be marked.
    @AssertTrue
    private boolean isDateAndScoreValid() {
        return (homeScore == null && awayScore == null) || date != null;
    }

    //  if the match is an upcoming one both scores must be null, if the match has a result both scores must be not null
    @AssertTrue
    private boolean isScoresValid() {
        return (homeScore == null && awayScore == null) || (homeScore !=null && awayScore != null);
    }

    //constructors
    public Match() {
    }

    public Match(LocalDate date, LocalTime time, Team homeTeam, Team awayTeam, Integer homeScore, Integer awayScore, Set<Team> teams) {
        this.date = date;
        this.time = time;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.teams = teams;
    }

    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long matchId) {
        this.id = matchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", teams=" + teams +
                '}';
    }
}




