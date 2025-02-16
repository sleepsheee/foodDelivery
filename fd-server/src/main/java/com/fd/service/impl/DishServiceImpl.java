package com.fd.service.impl;

import com.fd.constant.MessageConstant;
import com.fd.constant.StatusConstant;
import com.fd.dto.DishDTO;
import com.fd.dto.DishPageQueryDTO;
import com.fd.entity.Dish;
import com.fd.entity.DishFlavor;
import com.fd.exception.DeletionNotAllowedException;
import com.fd.repository.DishFlavorRepository;
import com.fd.repository.DishRepository;
import com.fd.repository.SetMealDishRepository;
import com.fd.repository.SetmealRepository;
import com.fd.service.DishService;
import com.fd.vo.DishVO;
import com.fd.vo.DishVOWithFlavor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private SetmealRepository setmealRepository;

    @Autowired
    private SetMealDishRepository setMealDishRepository;
    @Autowired
    private DishService dishService;

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

    public Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        Pageable pageable = PageRequest.of(dishPageQueryDTO.getPage() - 1,  dishPageQueryDTO.getPageSize(),  Sort.by("id").descending() );
        if(dishPageQueryDTO.getCategoryId() == null) {
            return dishRepository.find(null, dishPageQueryDTO.getStatus(), dishPageQueryDTO.getName(), pageable);
        }
        return dishRepository.find(dishPageQueryDTO.getCategoryId().longValue(), dishPageQueryDTO.getStatus(), dishPageQueryDTO.getName(), pageable);
    }

    @Transactional
    public void deleteBatch(List<Long> ids){
        //if the dish is on sell
        for(Long id : ids){
            Dish dish = dishRepository.findById(id).orElse(null);
            if(dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //if the dish is in set meal,getsetmealidbydishids
        List<Long> SetMealIds = setMealDishRepository.getSetMealIdbyDishId(ids);
        if(SetMealIds != null && !SetMealIds.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //delete dish flavor
        //delete with one sql 优化
        //delete from dish where id in()
        dishRepository.deleteAllByIdInBatch(ids);
        //TO DO 批量删除
        for(Long id : ids){
            dishFlavorRepository.deleteByDishId(id);
      }
    }

    public DishVOWithFlavor getByIdWithFlavor(Long id){
        //search dish by id
        Dish dish = dishRepository.findById(id).orElse(null);

        //search flavor by dishid
        List<DishFlavor> dishFlavorList = dishFlavorRepository.getAllByDishId(id);

        DishVOWithFlavor dishVOWithFlavor = new DishVOWithFlavor();
        BeanUtils.copyProperties(dish, dishVOWithFlavor);
        dishVOWithFlavor.setFlavors(dishFlavorList);
        return dishVOWithFlavor;
    }

    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        //因为口味的修改比较复杂，可能是追加口味，也可能是删除口味，也可能是全部更改口味
        //所以这里的统一操作为先删除，再按照实际的需求进行插入操作
        long stime = System.currentTimeMillis();
        Dish dish = new Dish();
        //字段没有的情况下就会更新为空，破坏了之前的原有字段！
        //由于jpa中没有现成的update方法，所以经常使用save方法来实现简单的更新操作，jpa会根据id的值自动识别，进行save和update的操作。当进行更改数据库的部分字段的时候，部分未修改的字段如果不传值的话，就会遇到问题，这些未修改的值就会按null进行赋值。
        dish = dishRepository.findById(dishDTO.getId()).orElse(null);
        BeanUtils.copyProperties(dishDTO, dish);

        dishRepository.save(dish);
        long etime = System.currentTimeMillis();
        System.out.printf("执行时长：%d 毫秒.", (etime - stime));

        //删除原有的口味数据
        dishFlavorRepository.deleteByDishId(dishDTO.getId());

        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });

            //向口味表插入n条数据
            dishFlavorRepository.saveAll(flavors);
        }
    }
}