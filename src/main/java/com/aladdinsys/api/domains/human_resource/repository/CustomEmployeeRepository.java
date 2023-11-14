package com.aladdinsys.api.domains.human_resource.repository;

import com.aladdinsys.api.domains.human_resource.entity.Employee;

import java.util.List;

public interface CustomEmployeeRepository {
    List<Employee> findEmployeeByDepartment(final Long departmentId);
}
