package com.fd.service;

import com.fd.dto.DishDTO;
import com.fd.dto.DishPageQueryDTO;
import com.fd.vo.DishVO;
import com.fd.vo.DishVOWithFlavor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVOWithFlavor getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);
}