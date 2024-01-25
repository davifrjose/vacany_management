package br.com.davifrjose.vacany_management.modules.candidate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.davifrjose.vacany_management.modules.candidate.entity.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

  
} 
  

