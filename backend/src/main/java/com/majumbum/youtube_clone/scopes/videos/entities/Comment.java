package com.majumbum.youtube_clone.scopes.videos.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String authorName;

    @Column
    private Long videoId;

    @Column(length=10485760)
    private String comment;

    @SuppressWarnings("unused")
    public Comment() { }

    public Comment(String authorName, Long videoId , String comment) {
        this.authorName = authorName;
        this.videoId = videoId;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", videoId=" + videoId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
