/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.repository;

import com.aladdinsys.api.domains.human_resource.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}
