package com.aladdinsys.api.domains.human_resource.repository;

import com.aladdinsys.api.domains.human_resource.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, CustomEmployeeRepository {

}
