package br.com.davifrjose.vacany_management.modules.candidate.useCases;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException()
  {
    super("Job not found");
  }
}
