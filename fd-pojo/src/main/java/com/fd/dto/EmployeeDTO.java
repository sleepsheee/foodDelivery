package com.fd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "save employee request param")
public class EmployeeDTO implements Serializable {
    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
