package com.aladdinsys.api.domains.grid.entity;

import com.aladdinsys.api.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_grid_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GridItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "preset_id", nullable = false, foreignKey = @ForeignKey(name = "fk_grid_item_preset_id"))
    private GridPreset preset;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "x", nullable = false)
    private Integer x;

    @Column(name = "y", nullable = false)
    private Integer y;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Builder
    public GridItem(Long id, GridPreset preset, Integer width, Integer height, Integer x, Integer y, Long contentId) {
        this.id = id;
        this.preset = preset;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.contentId = contentId;
    }
}
