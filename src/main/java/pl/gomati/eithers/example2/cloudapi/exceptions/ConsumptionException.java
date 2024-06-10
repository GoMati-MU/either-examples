package pl.gomati.eithers.example2.cloudapi.exceptions;

public class ConsumptionException extends Exception{

  public ConsumptionException(String message) {
    super(message);
  }

  public ConsumptionException(Throwable cause) {
    super(cause);
  }
}
