package fi.ollimyy.buttonleague.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Enter a name for the team")
    @Size(max = 50, message = "Team name must be no more than 50 characters")
    private String name;

    @ManyToMany(mappedBy = "teams")
    private Set<Match> matches = new HashSet<>();

    @OneToMany
    List<Player> players;

    //constructors
    public Team() {
    }

    public Team(String name, Set<Match> matches, List<Player> players) {
        this.name = name;
        this.matches = matches;
        this.players = players;
    }

    public Team(String name) {
        this.name = name;
    }

    //getters setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", matches=" + matches +
                ", players=" + players +
                '}';
    }
}
