package com.crudjava.victor.crudjava.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crudjava.victor.crudjava.dtos.CreatePostDto;
import com.crudjava.victor.crudjava.dtos.PostResponseDto;
import com.crudjava.victor.crudjava.dtos.UpdatePostDto;
import com.crudjava.victor.crudjava.entities.Post;
import com.crudjava.victor.crudjava.services.PostService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Long id) {
        Post postFound = postService.getPost(id);
        PostResponseDto post = new PostResponseDto(
                postFound.getId(),
                postFound.getTitle(),
                postFound.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts = postService.getAllPosts(pageable);
        List<PostResponseDto> mappedPosts = posts.stream().map((postEntity) -> {
            PostResponseDto post = new PostResponseDto(
                    postEntity.getId(),
                    postEntity.getTitle(),
                    postEntity.getContent());
            return post;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(mappedPosts);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostDto request) {
        Post postCreated = postService.createPost(request);
        PostResponseDto post = new PostResponseDto(
                postCreated.getId(),
                postCreated.getTitle(),
                postCreated.getContent());

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable("id") Long id, @RequestBody UpdatePostDto request) {
        Post postUpdated = postService.updatePost(id, request);
        PostResponseDto post = new PostResponseDto(
                postUpdated.getId(),
                postUpdated.getTitle(),
                postUpdated.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

}
