package pl.gomati.eithers.example2.cloudapi.exceptions;

public class CredentialsException extends Exception{

  public CredentialsException(String message) {
    super(message);
  }

  public CredentialsException(Throwable cause) {
    super(cause);
  }
}
