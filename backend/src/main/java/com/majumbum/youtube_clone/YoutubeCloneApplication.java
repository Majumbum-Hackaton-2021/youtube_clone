package com.majumbum.youtube_clone;

import com.majumbum.youtube_clone.scopes.videos.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class YoutubeCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeCloneApplication.class, args);
    }

}
