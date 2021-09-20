package com.majumbum.youtube_clone.scopes.videos.entities;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Video {
    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private String title;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private String author;

    @Column(length=10485760)
    @NotBlank
    @NotEmpty
    @NotNull
    private String description;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private Long views = 0L;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private Long likes = 0L;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private LocalDate dayPosted;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private String thumbnail;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private String channelThumbnail;

    @Column
    @Fetch(FetchMode.JOIN)
    @OneToMany
    private List<Comment> comments = new LinkedList<>();

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private String videoLink;

    @SuppressWarnings("unused")
    public Video() {
    }

    public Video(String title, String author, String description, String thumbnail, String channelThumbnail, String videoLink) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.dayPosted = LocalDate.now();
        this.thumbnail = thumbnail;
        this.channelThumbnail = channelThumbnail;
        this.videoLink = videoLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public LocalDate getDayPosted() {
        return dayPosted;
    }

    public void setDayPosted(LocalDate dayPosted) {
        this.dayPosted = dayPosted;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getChannelThumbnail() {
        return channelThumbnail;
    }

    public void setChannelThumbnail(String channelThumbnail) {
        this.channelThumbnail = channelThumbnail;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", likes=" + likes +
                ", dayPosted=" + dayPosted +
                ", thumbnail='" + thumbnail + '\'' +
                ", channelThumbnail='" + channelThumbnail + '\'' +
                ", comments=" + comments +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }
}
