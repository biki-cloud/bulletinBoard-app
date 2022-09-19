package com.example.bulletinboardapp.repository;

import com.example.bulletinboardapp.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
