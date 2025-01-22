package com.fd.controller.admin;

import com.fd.constant.JwtClaimsConstant;
import com.fd.dto.EmployeeLoginDTO;
import com.fd.entity.Employee;
import com.fd.properties.JwtProperties;
import com.fd.result.Result;
import com.fd.service.EmployeeService;
import com.fd.utils.JwtUtil;
import com.fd.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     *
     * @param employeeLoginDTO
     * @return
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
     *
     * @return
     */
    @ApiOperation("logout")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

}
