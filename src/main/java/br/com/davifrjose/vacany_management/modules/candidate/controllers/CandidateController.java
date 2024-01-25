package br.com.davifrjose.vacany_management.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.davifrjose.vacany_management.modules.candidate.CandidateEntity;
import br.com.davifrjose.vacany_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.davifrjose.vacany_management.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.davifrjose.vacany_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.davifrjose.vacany_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.davifrjose.vacany_management.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.davifrjose.vacany_management.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate info")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase  profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase  listAllJobsByFilterUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCaseList;

  @PostMapping("/")
  @Operation(summary = "Candidate registering", description = "This function is responsible for registering a candidate")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
        @Content(
          array = @ArraySchema(schema = @Schema(implementation = CandidateEntity.class))
        )
      }),
      @ApiResponse(responseCode = "400", description = "User already exists")
    } 
    )
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity)  
  {
    try {
      var result =  this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return  ResponseEntity.badRequest().body(e.getMessage());
    }
  }
    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Candidate profile", description = "This function is responsible for searching the candidate info")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
        @Content(
          array = @ArraySchema(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        )
      }),
      @ApiResponse(responseCode = "400", description = "User Not Found")
    } 
    )
    public ResponseEntity<Object> get(HttpServletRequest request){

      var idCandidate = request.getAttribute("candidate_id");
      try {
        var result = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
        return ResponseEntity.ok().body(result);
      } catch (Exception e) {
        // TODO: handle exception
        return  ResponseEntity.badRequest().body(e.getMessage());
      }
    }

    
    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "List of vacany available for the candidate", description = "This function is responsible for listing vacany available based on filter")
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
        @Content(
          array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
        )
      })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
      return this.listAllJobsByFilterUseCase.execute(filter);
    }
    
    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Aplication of a candidate to a vacany", description = "This function is responsible for executing the application of a candidate to a vacany")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob){

      var idCandidate = request.getAttribute("candidate_id");

      try {
        var result = this.applyJobCandidateUseCaseList.execute(UUID.fromString(idCandidate.toString()), idJob);
        return ResponseEntity.ok().body(result);
      } catch (Exception e) {
        // TODO: handle exception
        return  ResponseEntity.badRequest().body(e.getMessage());
      }
      
    }


  }

