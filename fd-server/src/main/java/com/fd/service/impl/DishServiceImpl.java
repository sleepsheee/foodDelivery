package com.fd.service.impl;

import com.fd.dto.DishDTO;
import com.fd.entity.Dish;
import com.fd.entity.DishFlavor;
import com.fd.repository.DishFlavorRepository;
import com.fd.repository.DishRepository;
import com.fd.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishFlavorRepository dishFlavorRepository;

    @Transactional
    public void saveWithFlavor(DishDTO dishDto) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDto, dish);
        log.info("{}{}", dishDto,dish);
        //insert 1 dish
        dishRepository.save(dish);
        Long DishId = dish.getId();

        //insert n flavor
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        if (dishFlavors != null && !dishFlavors.isEmpty()) {
            //set id value for dishFlavor using the primary key of dish
            dishFlavors.forEach(dishFlavor -> {dishFlavor.setDishId(DishId);});
            dishFlavorRepository.saveAll(dishFlavors);
        }
    }
}