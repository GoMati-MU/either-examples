package pl.gomati.exceptions;

public class DuplicatedMappingException extends Exception{

  public DuplicatedMappingException(String message) {
    super(message);
  }

  public DuplicatedMappingException(Throwable cause) {
    super(cause);
  }
}
