package com.fd.controller.admin;

import com.fd.dto.DishDTO;
import com.fd.dto.DishPageQueryDTO;
import com.fd.vo.DishVOWithFlavor;
import com.fd.result.PageResult;
import com.fd.result.Result;
import com.fd.service.DishService;
import com.fd.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish Interface")
@Slf4j

public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("New Dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("New Dish: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("Dish Page Query")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("Dish Page Query: {}", dishPageQueryDTO);
        Page<DishVO> dishVOPage = dishService.pageQuery(dishPageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(dishVOPage.getTotalElements());
        pageResult.setRecords(dishVOPage.getContent());
        return Result.success(pageResult);
    }

    /**
     * Delete Multiple Dish
     * param 1,2,3，RequestParam解析成list
     * return
     */
    @DeleteMapping
    @ApiOperation("Delete Dish")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Delete Dish: {}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Search Dish according to id
     * param id
     * return
     */
    @GetMapping("/{id}")
    @ApiOperation("Search Dish according to id")
    public Result<DishVOWithFlavor> getByIdWithFlavor(@PathVariable Long id) {
        log.info("Search Dish according to id: {}", id);
        DishVOWithFlavor dishVOWithFlavor = dishService.getByIdWithFlavor(id);
        return Result.success(dishVOWithFlavor);
    }

    /**
     * Update Dish
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update Dish")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("Update Dish {}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
}