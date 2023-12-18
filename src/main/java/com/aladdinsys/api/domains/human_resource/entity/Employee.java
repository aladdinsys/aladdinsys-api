/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "department_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_employee_department_id"))
  private Department department;

  @Builder
  public Employee(Long id, Department department, String name) {
    this.id = id;
    this.department = department;
    this.name = name;
  }

  public void update(String name, Department department) {
    this.name = name;
    this.department = department;
  }

  public void patchName(String name) {
    this.name = name;
  }

  public void patchDepartment(Department department) {
    this.department = department;
  }

  public void changeDepartment(Department department) {
    this.department = department;
  }
}
