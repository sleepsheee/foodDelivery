package com.fd.repository;

import com.fd.annotation.AutoFill;
import com.fd.entity.Employee;
import com.fd.enumeration.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long>, CrudRepository<Employee, Long> {
    Employee findByUsername(String username);

    Page<Employee> findByNameContaining(String name, Pageable pageable);
}
