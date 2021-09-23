package com.majumbum.youtube_clone.scopes.videos.entities;

public enum TestVideos {

    VIDEOS_ONE( "Astronomia" , "Admin",
            "Astronomia video downloaded on Meme", "astronomia.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "astronomia.mp4"),
    VIDEOS_TWO( "Drageur" , "Admin",
            "Drageur video downloaded on Meme", "drageur.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "drageur.mp4"),
    VIDEOS_THREE( "Girl" , "Admin",
            "Girl video downloaded on Meme", "girl.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "girl.mp4"),
    VIDEOS_FOUR( "Hehe" , "Admin",
            "Hehe video downloaded on Meme", "hehe.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "hehe.mp4"),
    VIDEOS_FIVE( "iphone" , "Admin",
            "Iphone video downloaded on Meme", "iphone.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "iphone.mp4"),
    VIDEOS_SIX( "Little Girl" , "Admin",
            "Little Girl video downloaded on Meme", "little.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "little.mp4"),
    VIDEOS_SEVEN( "Mask" , "Admin",
            "Mask video downloaded on Meme", "mask.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "mask.mp4"),
    VIDEOS_EIGHT( "Petit Africain" , "Admin",
            "Petit Africain video downloaded on Meme", "petit_qui_se_cache.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "petit_qui_se_cache.mp4"),
    VIDEOS_NINE( "Rire" , "Admin",
            "Rire video downloaded on Meme", "rire.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "rire.mp4"),
    VIDEOS_TEN( "What the Fuck" , "Admin",
            "What the Fuck video downloaded on Meme", "wtf.jpg",
            "https://avatars.dicebear.com/api/avataaars/male/1.svg", "wtf.mp4");

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
