package com.fd.controller.admin;

import com.fd.dto.DishDTO;
import com.fd.result.Result;
import com.fd.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}