package pl.gomati.eithers.example2.cloudapi.exceptions;

public class ResourceUnavailableException extends Exception{

  public ResourceUnavailableException(String message) {
    super(message);
  }

  public ResourceUnavailableException(Throwable cause) {
    super(cause);
  }
}
