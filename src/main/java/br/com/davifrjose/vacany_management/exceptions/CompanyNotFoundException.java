package br.com.davifrjose.vacany_management.exceptions;

public class CompanyNotFoundException  extends RuntimeException{
  public CompanyNotFoundException()
  {
    super("Company not found");
  }
}
