package com.fd.repository;

import com.fd.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Integer countByCategoryId(Long categoryId);
}