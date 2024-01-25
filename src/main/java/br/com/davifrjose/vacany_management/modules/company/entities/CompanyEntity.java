package br.com.davifrjose.vacany_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @Pattern(regexp = "\\S+", message="Username field cannot contain spaces")
  @NotBlank
  private String userName;

  @Email(message = "Email field must contain an valid email address")
  private String email;

  @Length(min = 10, max = 100, message = "Password field must contain at least 10 characters")
  private String password;

  private String name;
  private String website;
  private String description;


  @CreationTimestamp
  private LocalDateTime createdAT;
}
