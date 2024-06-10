package pl.gomati.exceptions;

public class MappingException extends Exception{

  public MappingException(String message) {
    super(message);
  }

  public MappingException(Throwable cause) {
    super(cause);
  }
}
