/* (C) 2023 */
package com.aladdinsys.api.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @CreatedDate
  @Column(name = "created_dt", nullable = false, updatable = false)
  private LocalDateTime createdDt;

  //    @CreatedBy
  //    @Column(name = "created_by", nullable = false, length = 100)
  //    private String createdBy;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @LastModifiedDate
  @Column(name = "modified_dt", nullable = false)
  private LocalDateTime modifiedDt;

  //    @LastModifiedBy
  //    @Column(name = "modified_by", nullable = false, length = 100)
  //    private String modifiedBy;

}
