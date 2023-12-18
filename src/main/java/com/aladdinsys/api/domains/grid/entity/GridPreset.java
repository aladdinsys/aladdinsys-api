/* (C) 2023 */
package com.aladdinsys.api.domains.grid.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_grid_preset")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GridPreset extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, unique = true, length = 20)
  private String name;

  @Column(name = "cols")
  private Integer cols;

  @Column(name = "rows")
  private Integer rows;

  @OneToMany(
      mappedBy = "preset",
      orphanRemoval = true,
      cascade = CascadeType.REMOVE,
      fetch = FetchType.LAZY)
  private Set<GridItem> items = new HashSet<>();

  @Builder
  public GridPreset(Long id, String name, Integer cols, Integer rows, Set<GridItem> items) {
    this.id = id;
    this.name = name;
    this.cols = cols;
    this.rows = rows;

    if (items != null) {
      this.items = items;
    }
  }

  public void addItem(GridItem savedItem) {
    this.items.add(savedItem);
  }

  public void removeItem(GridItem item) {
    this.items.remove(item);
  }

  public void clearItems() {
    this.items.clear();
  }

  public void update(String name, Integer cols, Integer rows) {
    this.name = name;
    this.cols = cols;
    this.rows = rows;
  }
}
