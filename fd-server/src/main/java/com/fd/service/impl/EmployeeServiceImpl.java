package com.fd.service.impl;

import com.fd.constant.MessageConstant;
import com.fd.constant.PasswordConstant;
import com.fd.constant.StatusConstant;
import com.fd.dto.EmployeeDTO;
import com.fd.dto.EmployeeLoginDTO;
import com.fd.dto.EmployeePageQueryDTO;
import com.fd.entity.Employee;
import com.fd.exception.AccountLockedException;
import com.fd.exception.AccountNotFoundException;
import com.fd.exception.PasswordErrorException;
import com.fd.repository.EmployeeRepository;
import com.fd.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Employee Login
     * param employeeLoginDTO
     * return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee =  employeeRepository.findByUsername(username);


        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * Employee save
     * param employeeDTO
     * return
     */
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // TODO change to the id of the user which is currently on
        employee.setCreateUser(10L);
        employee.setUpdateUser(10L);
        employeeRepository.save(employee);
    }

    /**
     * Employee search
     * param employeePageQueryDTO
     * return
     */
    public Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        Pageable pageable = PageRequest.of(employeePageQueryDTO.getPage() - 1,  employeePageQueryDTO.getPageSize(),  Sort.by("id").descending() );
        if(employeePageQueryDTO.getName() == null || employeePageQueryDTO.getName().isEmpty()) {
            return employeeRepository.findAll(pageable);
        }
        return employeeRepository.findByNameContaining(employeePageQueryDTO.getName(), pageable);
    }
}
