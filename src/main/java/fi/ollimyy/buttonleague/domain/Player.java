package fi.ollimyy.buttonleague.domain;

import jakarta.persistence.*;

@Entity
// number is unique within a team, no players with the same number in the same team
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"number", "team_id"}))
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    int number;


    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id")
    Team team;
    public Player() {
    }

    public Player(String firstName, String lastName, int number, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", number=" + number +
                ", team=" + team +
                '}';
    }
}
