package br.com.davifrjose.vacany_management.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException()
  {
    super("User already exists");
  }
}
