package com.example.letsgongbu.repository;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory);
    Optional<Post> findByTitle(String title);
}
