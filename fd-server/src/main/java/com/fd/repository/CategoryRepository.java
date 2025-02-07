package com.fd.repository;

import com.fd.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByNameContainingAndType(String name, Integer type, Pageable pageable);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    Page<Category> findByType(Integer type, Pageable pageable);

    List<Category> findByType(Integer type);
}