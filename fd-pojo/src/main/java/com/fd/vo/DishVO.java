package com.fd.vo;

import com.fd.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishVO implements Serializable {

    private Long id;
    //菜品名称
    private String name;
    //菜品分类id
    @Column(name = "category_id")
    private Long categoryId;
    //菜品价格
    private BigDecimal price;
    //图片
    private String image;
    //描述信息
    private String description;
    //0 停售 1 起售
    private Integer status;
    //更新时间
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    //分类名称
    @Column(name = "name")
    private String categoryName;
    //菜品关联的口味
//    private List<DishFlavor> flavors = new ArrayList<>();

    //private Integer copies;
}
