/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_department")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Builder
  public Department(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void update(String name) {
    this.name = name;
  }
}
