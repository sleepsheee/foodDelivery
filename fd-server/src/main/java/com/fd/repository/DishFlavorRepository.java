package com.fd.repository;

import com.fd.entity.DishFlavor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishFlavorRepository extends JpaRepository<DishFlavor, Long> {
    void deleteByDishId(Long dishId);

    List<DishFlavor> getAllByDishId(Long dishId);
}