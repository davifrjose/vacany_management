package br.com.davifrjose.vacany_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "job")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Schema(example = "vacany for designer")
  private String description;
  @Schema(example = "GymPass, HealthPlan")
  private String benefits;

  @NotBlank(message = "This field is required")
  @Schema(example = "Senior")
  private String level;

  @ManyToOne
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private CompanyEntity companyEntity;

  @Column(name = "company_id", nullable = false)
  private UUID company_id;

  @CreationTimestamp
  private LocalDateTime createdAT;

}
