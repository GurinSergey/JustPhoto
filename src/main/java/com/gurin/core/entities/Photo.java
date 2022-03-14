package com.gurin.core.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SGurin on 24.03.2016.
 */
@Entity
@Table(name = "Photo")
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonIgnore
    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Transient
    private String encoded;

    @Column(name = "totalPoints")
    private double totalPoints;

    @Transient
    private boolean alreadyEvaluatedByCurrentUser;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "photo")
    @JsonManagedReference
    private List<Comment> comment = new ArrayList<Comment>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "photo")
    private List<Evaluation> evaluation = new ArrayList<Evaluation>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(Comment cmt) {
        comment.add(cmt);
        cmt.setPhoto(this);
    }

    public List<Evaluation> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation eval) {
        evaluation.add(eval);
        eval.setPhoto(this);
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isAlreadyEvaluatedByCurrentUser() {
        return alreadyEvaluatedByCurrentUser;
    }

    public void setAlreadyEvaluatedByCurrentUser(boolean alreadyEvaluatedByCurrentUser) {
        this.alreadyEvaluatedByCurrentUser = alreadyEvaluatedByCurrentUser;
    }
}
