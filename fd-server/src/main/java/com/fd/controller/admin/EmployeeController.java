package com.fd.controller.admin;

import com.fd.constant.JwtClaimsConstant;
import com.fd.dto.EmployeeDTO;
import com.fd.dto.EmployeeLoginDTO;
import com.fd.dto.EmployeePageQueryDTO;
import com.fd.entity.Employee;
import com.fd.properties.JwtProperties;
import com.fd.result.PageResult;
import com.fd.result.Result;
import com.fd.service.EmployeeService;
import com.fd.utils.JwtUtil;
import com.fd.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Employee Management
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "Employee Management Interface")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Login
     * param employeeLoginDTO
     * return
     */
    @ApiOperation("login")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("Employee Login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //produce jwt token after login successfully
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * Logout
     * return
     */
    @ApiOperation("logout")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * save
     * param employeeDTO
     * return
     */
    @ApiOperation("save")
    @PostMapping()
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("save a new employee：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * search
     * param employeePageQueryDTO
     * return
     */
    @ApiOperation("search")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("search employee：{}", employeePageQueryDTO);
        Page<Employee> employeePage = employeeService.pageQuery(employeePageQueryDTO);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(employeePage.getTotalElements());
        pageResult.setRecords(employeePage.getContent());
        return Result.success(pageResult);
    }

    /**
     * Lock and Unlock account
     * param status, id
     * return
     */
    @ApiOperation("Lock or Unlock employee account")
    @PostMapping("/status/{status}")
    public Result lockOrUnLock(@PathVariable Integer status, Long id ) {
        log.info("Lock or Unlock employee account{},{}", status, id);
        employeeService.lockOrUnLock(status, id);
        return Result.success();
    }

    /**
     * get employee by id
     * param id
     * return
     */
    @GetMapping("/{id}")
    @ApiOperation("Get Employee by id")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("Get employee {}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * update employee
     * param employeeDTO
     * return
     */
    @PutMapping
    @ApiOperation("Update Employee")
    public Result Update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Update employee {}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }



}
