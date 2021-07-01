package com.mountblue.hackernews.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name="questions")
@Table
public class Ask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;


    @Column(name = "question")
    private String question;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "points")
    private int points;

    @Column(name="created_at", length = 10000)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "update_at")
    @CreationTimestamp
    private Timestamp updatedAt;



   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ask_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

   /* public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }*/



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Ask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", userName='" + userName + '\'' +
                ", points=" + points +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
               // ", comments=" + comments +
                '}';
    }
}
