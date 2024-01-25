package br.com.davifrjose.vacany_management.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.davifrjose.vacany_management.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
  // "contains - like"
  // Select * from job where description like %filter%
  List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);

}
