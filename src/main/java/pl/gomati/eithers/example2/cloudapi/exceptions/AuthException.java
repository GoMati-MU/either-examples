package pl.gomati.eithers.example2.cloudapi.exceptions;

public class AuthException extends Exception{

  public AuthException(String message) {
    super(message);
  }

  public AuthException(Throwable cause) {
    super(cause);
  }
}
