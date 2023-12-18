/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.repository;

import com.aladdinsys.api.domains.human_resource.entity.Employee;
import com.aladdinsys.api.domains.human_resource.entity.QEmployee;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Employee> findEmployeeByDepartment(Long departmentId) {
    return queryFactory
        .select(QEmployee.employee)
        .from(QEmployee.employee)
        .where(QEmployee.employee.department.id.eq(departmentId))
        .stream()
        .toList();
  }
}
