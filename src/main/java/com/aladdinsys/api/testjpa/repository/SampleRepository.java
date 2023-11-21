package com.aladdinsys.api.testjpa.repository;

import com.aladdinsys.api.testjpa.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

}
