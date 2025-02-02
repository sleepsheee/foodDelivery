package com.fd.service.impl;

import com.fd.repository.CategoryRepository;
import com.fd.repository.DishRepository;
import com.fd.constant.MessageConstant;
import com.fd.constant.StatusConstant;
import com.fd.context.BaseContext;
import com.fd.dto.CategoryDTO;
import com.fd.dto.CategoryPageQueryDTO;
import com.fd.entity.Category;
import com.fd.exception.DeletionNotAllowedException;
import com.fd.repository.SetmealRepository;
import com.fd.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private SetmealRepository setmealRepository;

    /**
     * Add Category
     * param categoryDTO
     */
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        //属性拷贝
        BeanUtils.copyProperties(categoryDTO, category);

        //分类状态默认为禁用状态0
        category.setStatus(StatusConstant.DISABLE);

        //设置创建时间、修改时间、创建人、修改人
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryRepository.save(category);
    }

    /**
     * Category Page Query
     * param categoryPageQueryDTO
     * return
     */
    public Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        Pageable pageable = PageRequest.of(categoryPageQueryDTO.getPage() - 1,  categoryPageQueryDTO.getPageSize(),  Sort.by("id").descending() );
        if(categoryPageQueryDTO.getName() == null && categoryPageQueryDTO.getType() == null) {
            return categoryRepository.findAll(pageable);
        }
        else if(categoryPageQueryDTO.getName() == null) {
            return categoryRepository.findByType(categoryPageQueryDTO.getType(), pageable);
        }
        else if(categoryPageQueryDTO.getType() == null) {
            return categoryRepository.findByNameContaining(categoryPageQueryDTO.getName(), pageable);
        }
        return categoryRepository.findByNameContainingAndType(categoryPageQueryDTO.getName(), categoryPageQueryDTO.getType(), pageable);
    }

    /**
     * delete category according to id
     * param id
     */
    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishRepository.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setmealRepository.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //删除分类数据
        categoryRepository.deleteById(id);
    }

    /**
     * edit category
     * param categoryDTO
     */
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //设置修改时间、修改人
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryRepository.save(category);
    }

    /**
     * Lock and Unlock category
     * param status
     * param id
     */
    public void startOrStop(Integer status, Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());;
        categoryRepository.save(category);
    }
}