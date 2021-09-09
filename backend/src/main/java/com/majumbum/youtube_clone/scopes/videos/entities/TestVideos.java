package com.majumbum.youtube_clone.scopes.videos.entities;

public enum TestVideos {

    VIDEOS_ONE( "UN" , "Admin",
            "UNO test video with this description", "one.jpg",
            "https://avatars.dicebear.com/api/male/302.svg", "one.mvk"),
    VIDEOS_TWO( "DEUX" , "Admin",
            "DEUX test video with this description", "two.jpg",
            "https://avatars.dicebear.com/api/male/302.svg", "two.mvk"),
    VIDEOS_THREE( "TROIS" , "Admin",
            "TROIS test video with this description", "three.jpg",
            "https://avatars.dicebear.com/api/male/302.svg", "tree.mvk"),
    VIDEOS_FOUR( "QUATRE" , "Admin",
            "QUATRE test video with this description", "four.jpg",
            "https://avatars.dicebear.com/api/male/302.svg", "four.mvk"),
    VIDEOS_FIVE( "CINQ" , "Admin",
            "CINQ test video with this description", "five.jpg",
            "https://avatars.dicebear.com/api/male/302.svg", "five.mvk");

    public String title;
    public String description;
    public String thumbnail;
    public String channelThumbnail;
    public String author;
    public String videoLink;

    TestVideos(String title, String author, String description, String thumbnail ,String channelThumbnail,  String videoLink) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.channelThumbnail = channelThumbnail;
        this.author = author;
        this.videoLink = videoLink;
    }
}
