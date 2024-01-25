package br.com.davifrjose.vacany_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
  @Schema(example = "Vacany for junior developer", requiredMode = RequiredMode.REQUIRED)
  private String description;
  @Schema(example = "GymPass,, HealthPLan", requiredMode = RequiredMode.REQUIRED)
  private String benefits;
  @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
  private String level;
}
