package com.majumbum.youtube_clone.scopes.videos.controller;

import com.majumbum.youtube_clone.YoutubeCloneApplication;
import com.majumbum.youtube_clone.scopes.auth.payload.response.MessageResponse;
import com.majumbum.youtube_clone.scopes.videos.entities.Comment;
import com.majumbum.youtube_clone.scopes.videos.entities.Video;
import com.majumbum.youtube_clone.scopes.videos.services.VideoService;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public List<Video> getAllVideos(){
        return videoService.getVideos();
    }

    @PostMapping("/addVideo")
    public ResponseEntity<?> addVideo(@RequestBody Optional<Video> video){
        if(video.isEmpty())
            return ResponseEntity.ok(new MessageResponse("video param is needed"));

        videoService.saveNewVideo(video.get());
        return ResponseEntity.ok(new MessageResponse("Video was uploaded"));
    }

    @GetMapping("/view")
    public ResponseEntity<?> addView(@RequestParam("id") Optional<Long> videoId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));

        videoService.updateViews(videoId.get());
        return ResponseEntity.ok(new MessageResponse("Video up to date"));
    }

    @GetMapping("/like")
    public ResponseEntity<?> addLike(@RequestParam("id") Optional<Long> videoId , @RequestParam("userId") Optional<Long> userId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));

        if(userId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("userId param is needed"));

        videoService.updateLikes(videoId.get(), userId.get());
        return ResponseEntity.ok(new MessageResponse("Video up to date"));
    }


    @GetMapping("/isLiked")
    public ResponseEntity<?> isLiked(@RequestParam("id") Optional<Long> videoId , @RequestParam("userId") Optional<Long> userId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));

        if(userId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("userId param is needed"));

        return ResponseEntity.ok(videoService.isLiked(videoId.get(),userId.get()));
    }
    
    @GetMapping("/removeLike")
    public ResponseEntity<?> removeLike(@RequestParam("id") Optional<Long> videoId , @RequestParam("userId") Optional<Long> userId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));

        if(userId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("userId param is needed"));

        videoService.removeLikes(videoId.get(), userId.get());
        return ResponseEntity.ok(new MessageResponse("Video up to date"));
    }

    @GetMapping("/unique")
    public ResponseEntity<?> getVideo(@RequestParam("id") Optional<Long> videoId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));
        Optional<Video> video = videoService.getVideoById(videoId.get());

        if(video.isEmpty())
            return ResponseEntity.ok(new MessageResponse("video dont exist"));

        return ResponseEntity.ok(video.get());
    }


    @GetMapping(value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@RequestParam("imageName") Optional<String> name) throws IOException {

        if(name.isEmpty())
            return ResponseEntity.ok(new MessageResponse("imageName param is needed"));

        var imgFile = new PathResource("upload-dir/"+ name.get());
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

    @GetMapping(value = "/videoFile",
            produces ="video/mp4")
    public ResponseEntity<?> getVideoFile(@RequestParam("videoName") Optional<String> name) throws IOException {

        if(name.isEmpty())
            return ResponseEntity.ok(new MessageResponse("videoName param is needed"));

        var videoFile = new PathResource("upload-dir/"+ name.get());
        byte[] bytes = StreamUtils.copyToByteArray(videoFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(bytes);
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody Comment comment){

        if(comment == null)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Comment invalid"));

        videoService.addComment(comment);

        return ResponseEntity.ok(new MessageResponse("Comment successfully added"));
    }

    @GetMapping("/deletevideo")
    public ResponseEntity<?> deleteVideo(@RequestParam("id") Optional<Long> videoId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));

        videoService.deleteVideoById(videoId.get());
        return ResponseEntity.ok(new MessageResponse("Video is deleted"));
    }

    @GetMapping("/deleteComment")
    public ResponseEntity<?> deleteComment(@RequestParam("id") Optional<Long> commentId){
        if(commentId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("id param is needed"));

        Optional<Video> video = videoService.deleteCommentById(commentId.get());

        if(video.isEmpty())
            ResponseEntity.ok(new MessageResponse("Video dont exist"));

        return ResponseEntity.ok(video.get());
    }

    @GetMapping("/saveVideo")
    public ResponseEntity<?> SaveVideo(@RequestParam("videoId") Optional<Long> videoId , @RequestParam("userId") Optional<Long> userId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("videoId param is needed"));

        if(userId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("userId param is needed"));

        videoService.addSavedVideo(videoId.get(), userId.get());
        return ResponseEntity.ok(new MessageResponse("Video is saved"));
    }

    @GetMapping("/removeSavedVideo")
    public ResponseEntity<?> removeSavedVideo(@RequestParam("videoId") Optional<Long> videoId , @RequestParam("userId") Optional<Long> userId){
        if(videoId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("videoId param is needed"));

        if(userId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("userId param is needed"));

        videoService.removeSavedVideo(videoId.get(), userId.get());
        return ResponseEntity.ok(new MessageResponse("Video is removed"));
    }

    @GetMapping("/savedVideos")
    public ResponseEntity<?> savedVideo(@RequestParam("userId") Optional<Long> userId){
        if(userId.isEmpty())
            return ResponseEntity.ok(new MessageResponse("userId param is needed"));

        return ResponseEntity.ok(videoService.getSavedVideosFromUser(userId.get()));
    }
}
