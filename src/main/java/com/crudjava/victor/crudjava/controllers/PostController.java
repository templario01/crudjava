package com.crudjava.victor.crudjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crudjava.victor.crudjava.dtos.CreatePostDto;
import com.crudjava.victor.crudjava.dtos.PostResponseDto;
import com.crudjava.victor.crudjava.entities.Post;
import com.crudjava.victor.crudjava.services.PostService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostDto request) {
        Post postCreated = postService.createPost(request);
        PostResponseDto post = new PostResponseDto(
                postCreated.getId(),
                postCreated.getTitle(),
                postCreated.getContent());

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Long id){
        postService.deletePost(id);
    }

}
