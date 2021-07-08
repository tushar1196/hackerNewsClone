package com.mountblue.hackernews.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "points")
    private int points;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "comment_vote_up",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinTable(name = "answer_vote_up",
//            joinColumns = @JoinColumn(name = "answer_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersVotedUp;

    public List<User> getUsersVotedUp() {
        return usersVotedUp;
    }

    public void setUsersVotedUp(List<User> usersVotedUp) {
        this.usersVotedUp = usersVotedUp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", points=" + points +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", usersVotedUp=" + usersVotedUp +
                '}';
    }
}
