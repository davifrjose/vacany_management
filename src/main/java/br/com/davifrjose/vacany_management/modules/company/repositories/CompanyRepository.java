package br.com.davifrjose.vacany_management.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.davifrjose.vacany_management.modules.company.entities.CompanyEntity;



public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
Optional<CompanyEntity> findByUserNameOrEmail(String userName, String email);

Optional<CompanyEntity> findByUserName(String userName);
}
