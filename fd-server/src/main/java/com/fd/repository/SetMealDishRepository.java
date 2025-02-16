package com.fd.repository;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.fd.entity.SetmealDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SetMealDishRepository extends JpaRepository<SetmealDish, Long> {
    @Query("SELECT sd.dishId FROM SetmealDish sd WHERE sd.dishId IN (?1)")
    List<Long> getSetMealIdbyDishId(List<Long> dishIds);
}
