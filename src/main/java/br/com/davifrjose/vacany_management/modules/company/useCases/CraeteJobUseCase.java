package br.com.davifrjose.vacany_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davifrjose.vacany_management.exceptions.CompanyNotFoundException;
import br.com.davifrjose.vacany_management.modules.company.entities.JobEntity;
import br.com.davifrjose.vacany_management.modules.company.repositories.CompanyRepository;
import br.com.davifrjose.vacany_management.modules.company.repositories.JobRepository;

@Service
public class CraeteJobUseCase {
  
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;
  
  public JobEntity execute(JobEntity jobEntity){
    this.companyRepository.findById(jobEntity.getCompany_id()).orElseThrow(()-> {
      throw new CompanyNotFoundException();
    });
    
    return this.jobRepository.save(jobEntity);
  }
}
