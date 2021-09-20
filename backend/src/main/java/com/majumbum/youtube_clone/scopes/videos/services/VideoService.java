package com.majumbum.youtube_clone.scopes.videos.services;

import com.majumbum.youtube_clone.scopes.user.entities.User;
import com.majumbum.youtube_clone.scopes.user.repositories.UserRepository;
import com.majumbum.youtube_clone.scopes.videos.entities.Comment;
import com.majumbum.youtube_clone.scopes.videos.entities.Video;
import com.majumbum.youtube_clone.scopes.videos.repository.CommentRepository;
import com.majumbum.youtube_clone.scopes.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveVideo(Video video){
        if(video == null) throw new NullPointerException("Video must no be null");
        videoRepository.save(video);
    }

    public void saveNewVideo(Video video){
        if(video == null) throw new NullPointerException("Video must no be null");
        video.setDayPosted(LocalDate.now());
        videoRepository.save(video);
    }

    public List<Video> getVideos(){
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(long id){
        return videoRepository.findById(id);
    }

    public void addComment(Comment comment){
        if(comment == null) throw new NullPointerException("comment should not be null");
        if(comment.getVideoId() == null) throw new NullPointerException("VideoID should not be null");

        Optional<Video> video = getVideoById(comment.getVideoId());
        if(video.isEmpty()) throw new NullPointerException("Video is not in database");

        commentRepository.save(comment);
        video.get().getComments().add((comment));
        saveVideo(video.get());
    }

    public void deleteVideoById(long id){

        Optional<Video> video = getVideoById(id);
        if(video.isEmpty()) throw new NullPointerException("Video is not in database");

        deleteAllComments(video.get());
        videoRepository.deleteById(id);
    }

    public List<Comment> getCommentsByVideo(Long videoId){
        if(videoId == null) throw new NullPointerException("VideoID should not be null");

        Optional<Video> video = getVideoById(videoId);
        if(video.isEmpty()) throw new NullPointerException("Video is not in database");

        return new ArrayList<>(video.get().getComments());
    }


    public Optional<Comment> getCommentById(Long commentId){
        return commentRepository.findById(commentId);
    }


    public Optional<Video> deleteCommentById(Long commentId){
        if(commentId == null) throw new NullPointerException("comment should not be null");

        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isEmpty()) throw new NullPointerException("comment should not be null");

        Optional<Video> video = getVideoById(comment.get().getVideoId());

        if(video.isEmpty()) throw new NullPointerException("Video is not in database");

        video.get().getComments().removeIf(c -> c.getVideoId().equals(comment.get().getVideoId()) && c.getAuthorName().equals(comment.get().getAuthorName())
                && c.getComment().equals(comment.get().getComment()));

        saveVideo(video.get());

        return video;
    }

    public void deleteAllComments(Video video){
        if(video == null) throw new NullPointerException("Video must not be null");

        for(Comment comment : video.getComments()){
            video.getComments().remove(comment);
            commentRepository.delete(comment);
        }
    }

    public void updateLikes(Long videoId) {
        if(videoId == null) throw new NullPointerException("VideoID should not be null");

        Optional<Video> video = getVideoById(videoId);
        if(video.isEmpty()) throw new NullPointerException("Video is not in database");

        video.get().setLikes(video.get().getLikes()+ 1);
        saveVideo(video.get());
    }

    public void updateViews(Long videoId) {
        if(videoId == null) throw new NullPointerException("VideoID should not be null");

        Optional<Video> video = getVideoById(videoId);
        if(video.isEmpty()) throw new NullPointerException("Video is not in database");

        video.get().setViews(video.get().getViews()+ 1);
        saveVideo(video.get());
    }

    public void addSavedVideo(Long videoId, Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new NullPointerException("The user with this Id doesn't exist");
        user.get().addVideoId(videoId);
        userRepository.save(user.get());
    }

    public void removeSavedVideo(Long videoId, Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new NullPointerException("The user with this Id doesn't exist");
        user.get().removeVideoId(videoId);
        userRepository.save(user.get());
    }

    public Set<Video> getSavedVideosFromUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new NullPointerException("The user with this Id doesn't exist");

        Set<Video> videos = new HashSet<>();
        for(Long id : user.get().getSavedVideoId()){
            Optional<Video> video = getVideoById(id);
            if(video.isEmpty()) throw new NullPointerException("Video is not in database");

            videos.add(video.get());
        }
        return videos;
    }
}
