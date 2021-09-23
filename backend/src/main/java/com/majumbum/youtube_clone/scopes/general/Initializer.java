package com.majumbum.youtube_clone.scopes.general;


import com.majumbum.youtube_clone.scopes.user.entities.TestUser;
import com.majumbum.youtube_clone.scopes.user.entities.User;
import com.majumbum.youtube_clone.scopes.user.services.UserService;
import com.majumbum.youtube_clone.scopes.videos.entities.TestVideos;
import com.majumbum.youtube_clone.scopes.videos.entities.Video;
import com.majumbum.youtube_clone.scopes.videos.services.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    public UserService userService;
    public VideoService videoService;

    @Autowired
    public Initializer(UserService userService, VideoService videoService,
                       final @Value("${spring.jpa.hibernate.ddl-auto}") String database ,
                       final @Value("${spring.profiles.active}") String profile){

        this.userService = userService;
        this.videoService = videoService;
        if(setupDemoUsers(database)){
            setupTestUsers();
            setupTestVideos();
        }
    }

    private void setupTestUsers() {
        LOG.info("Creating default Test Users.");
        User user ;
        for (TestUser testUser : TestUser.values()) {
            user = new User(testUser.firstname , testUser.lastname , testUser.email, testUser.nickname,
                    testUser.password , testUser.grantedAuthorities , testUser.gender);

            if(user.getEmail().equals("admin@mail.com"))
                user.setImage("https://avatars.dicebear.com/api/avataaars/male/1.svg");

            userService.generateAndSaveNewValidationTokenForUser(user);
            userService.rehashPassword(testUser.password , user);
        }
    }

    private void setupTestVideos(){
        LOG.info("Creating default videos.");
        Video video;
        for(TestVideos testVideos : TestVideos.values()){
            video = new Video(testVideos.title, testVideos.author, testVideos.description, testVideos.thumbnail , testVideos.channelThumbnail, testVideos.videoLink);
            videoService.saveVideo(video);
        }
    }

    private boolean setupDemoUsers(String database){
        return "create".equals(database);
    }
}
