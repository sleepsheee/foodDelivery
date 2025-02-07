package com.fd.controller.admin;

import com.fd.dto.CategoryDTO;
import com.fd.dto.CategoryPageQueryDTO;
import com.fd.entity.Category;
import com.fd.result.PageResult;
import com.fd.result.Result;
import com.fd.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "Category Interface")
@Slf4j

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * Add Category
     * param categoryDTO
     * return
     */
    @PostMapping
    @ApiOperation("Add Category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("Add Category：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * Category Page Query
     * param categoryPageQueryDTO
     * return
     */
    @GetMapping("/page")
    @ApiOperation("Category Page Query")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("Category Page Query：{}", categoryPageQueryDTO);
        Page<Category> categoryPage = categoryService.pageQuery(categoryPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(categoryPage.getTotalElements());
        pageResult.setRecords(categoryPage.getContent());
        return Result.success(pageResult);
    }

    /**
     * delete category
     * param id
     * return
     */
    @DeleteMapping
    @ApiOperation("delete category")
    public Result<String> deleteById(Long id){
        log.info("delete category：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * edit category
     * param categoryDTO
     * return
     */
    @PutMapping
    @ApiOperation("edit category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * Lock and Unlock category
     * param status
     * param id
     * return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Lock and Unlock category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Search category according to type")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
