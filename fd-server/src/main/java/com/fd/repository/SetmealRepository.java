package com.fd.repository;

import com.fd.entity.Setmeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetmealRepository extends JpaRepository<Setmeal, Long>{
    Integer countByCategoryId(Long categoryId);
}