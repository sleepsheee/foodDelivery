package com.fd.service;

import com.fd.dto.EmployeeDTO;
import com.fd.dto.EmployeeLoginDTO;
import com.fd.dto.EmployeePageQueryDTO;
import com.fd.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    /**
     * Employee Login
     * param employeeLoginDTO
     * return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * Employee save
     * param employeeDTO
     * return
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * Employee search
     * param employeePageQueryDTO
     * return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Lock and Unlock account
     * param status, id
     * return
     */
    void lockOrUnLock(Integer status, Long id);

}
