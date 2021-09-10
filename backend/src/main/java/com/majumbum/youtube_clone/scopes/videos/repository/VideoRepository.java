package com.majumbum.youtube_clone.scopes.videos.repository;

import com.majumbum.youtube_clone.scopes.videos.entities.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video, Long> {
    @Override
    List<Video> findAll();
}
