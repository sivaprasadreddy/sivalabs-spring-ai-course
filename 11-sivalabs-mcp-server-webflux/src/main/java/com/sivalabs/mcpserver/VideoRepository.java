package com.sivalabs.mcpserver;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByTitle(String title);

    List<Video> findByTitleContainingIgnoreCase(String title);
}