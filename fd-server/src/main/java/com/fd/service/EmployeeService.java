package com.fd.service;

import com.fd.dto.EmployeeDTO;
import com.fd.dto.EmployeeLoginDTO;
import com.fd.entity.Employee;
import com.fd.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface EmployeeService {

    /**
     * Employee Login
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * Employee save
     * @param employeeDTO
     * @return
     */
    void save(EmployeeDTO employeeDTO);

}
