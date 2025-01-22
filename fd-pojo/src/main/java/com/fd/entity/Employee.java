package com.fd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    @Column(name = "id_number")
    private String idNumber;

    private Integer status;

    @Column(name = "create_time")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_user")
    private Long updateUser;

}
