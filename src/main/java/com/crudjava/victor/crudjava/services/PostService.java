package com.crudjava.victor.crudjava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.crudjava.victor.crudjava.dtos.CreatePostDto;
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
