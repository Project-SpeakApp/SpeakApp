package com.speakapp.postservice.repositories;

import com.speakapp.postservice.entities.Post;
import com.speakapp.postservice.entities.PostReaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReactionRepository {

    List<PostReaction> findAllByPost(Post post);
}

