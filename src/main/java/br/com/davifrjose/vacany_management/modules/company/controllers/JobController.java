package br.com.davifrjose.vacany_management.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.davifrjose.vacany_management.modules.company.dto.CreateJobDTO;
import br.com.davifrjose.vacany_management.modules.company.entities.JobEntity;
import br.com.davifrjose.vacany_management.modules.company.useCases.CraeteJobUseCase;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/company/job")
public class JobController {

  @Autowired
  private CraeteJobUseCase createJobUseCase;
  

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  @Tag(name = "Vacany", description = "Vacany info")
    @Operation(summary = "register vacany ", description = "This function is responsible for registering vacany of a company")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
        @Content(
         schema = @Schema(implementation = JobEntity.class)
        )
      })
    })
    @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
      
      var companyId = request.getAttribute("company_id");

      try {
      var jobEntity = JobEntity.builder()
      .benefits(createJobDTO.getBenefits())
      .company_id(UUID.fromString(companyId.toString()))
      .description(createJobDTO.getDescription())
      .level(createJobDTO.getLevel())
      .build();


      
      var result = this.createJobUseCase.execute(jobEntity);
      return ResponseEntity.ok().body(result);
      }catch(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
}
  }
  
}
