package com.fd.service;

import com.fd.dto.CategoryDTO;
import com.fd.dto.CategoryPageQueryDTO;
import com.fd.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    /**
     * Add Category
     * param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * Category Page Query
     * param categoryPageQueryDTO
     * return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * delete category by id
     * param id
     */
    void deleteById(Long id);

    /**
     * edit category
     * param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * Lock and Unlock category
     * param status
     * param id
     */
    void startOrStop(Integer status, Long id);

    List<Category> list(Integer type);
}