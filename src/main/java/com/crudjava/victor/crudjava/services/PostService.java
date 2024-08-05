package com.crudjava.victor.crudjava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.crudjava.victor.crudjava.dtos.CreatePostDto;
import com.crudjava.victor.crudjava.dtos.UpdatePostDto;
import com.crudjava.victor.crudjava.entities.Post;
import com.crudjava.victor.crudjava.repositories.PostRepository;



@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post createPost(CreatePostDto request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return postRepository.save(post);
    }

    public Post getPost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw this.throwNotFoundException();
        }
        return post.get();
    }

    public List<Post> getAllPosts(Pageable pageable){
       Page<Post> pagePost = postRepository.findAll(pageable);

       return pagePost.getContent();

    }

    @Transactional
    public Post updatePost(Long id, UpdatePostDto request) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw this.throwNotFoundException();
        }
        Post newPost = new Post();
        newPost.setId(id);
        newPost.setTitle(request.getTitle());
        newPost.setContent(request.getContent());
        return postRepository.save(newPost);
    }

    @Transactional
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw this.throwNotFoundException();
        }
        postRepository.deleteById(id);
    }

    private ResponseStatusException throwNotFoundException() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
