package com.aladdinsys.api.testjpa.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_sampleJpa")
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long data_id;
    public String std_yy;
    public String phsrtn_fclty_lclas_nm;
    public String phsrtn_fclty_mclas_nm;
    public String iem_nm;
    public String data_val;
}
