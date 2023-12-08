package com.speakapp.postservice;

import com.speakapp.postservice.dtos.*;
import com.speakapp.postservice.entities.*;

import com.speakapp.postservice.mappers.PostMapper;
import com.speakapp.postservice.repositories.CommentReactionRepository;
import com.speakapp.postservice.repositories.CommentRepository;
import com.speakapp.postservice.repositories.PostReactionRepository;
import com.speakapp.postservice.repositories.PostRepository;
import com.speakapp.postservice.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.UUID;


@SpringBootTest
class PostServiceApplicationTests {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private PostReactionRepository postReactionRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentReactionRepository commentReactionRepository;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private PostService postService;


	PostServiceApplicationTests() {
	}


	@Test
	void contextLoads() {
	}




}
