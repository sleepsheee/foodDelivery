package com.fd.repository;

import com.fd.entity.Category;
import com.fd.entity.Dish;
import com.fd.entity.DishFlavor;
import com.fd.vo.DishVO;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Integer countByCategoryId(Long categoryId);

//select d.id, d.price,c.name from Dish d left outer join Category c on d.category_id = c.id left outer join dish_flavor df on d.id = df.dish_id where d.status = 0 and d.category_id = null;
    @Query("select new com.fd.vo.DishVO(d.id, d.name, d.categoryId, d.price, d.image," +
            "d.description, d.status, d.updateTime, c.name) from Dish d, Category c where d.categoryId = c.id " +
            "and (?1 is null or d.categoryId = ?1) and (?2 is null or d.status = ?2) and (?3 is null or d.name like concat('%', ?3, '%'))")
    Page<DishVO> find(@Param("categoryId") Long categoryId, @Param("status") Integer status, @Param("name") String name, Pageable pageable);
}