package pl.gomati.eithers.example2.cloudapi.exceptions;

public class LoginException extends Exception{

  public LoginException(String message) {
    super(message);
  }

  public LoginException(Throwable cause) {
    super(cause);
  }
}
