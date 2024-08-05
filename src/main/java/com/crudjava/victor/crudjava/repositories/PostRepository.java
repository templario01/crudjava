package com.crudjava.victor.crudjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudjava.victor.crudjava.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
