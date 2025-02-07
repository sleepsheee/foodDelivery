package com.fd.repository;

import com.fd.entity.DishFlavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishFlavorRepository extends JpaRepository<DishFlavor, Long> {
}