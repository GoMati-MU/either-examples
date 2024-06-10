package pl.gomati.eithers.example1;

import cyclops.control.Either;
import lombok.Getter;
import pl.gomati.exceptions.ConfigException;

@Getter //other classes get nice String instead of Either which they have to handle
public class VenkatStringWrappedNumber {
  final String valueWithNumber;

  public VenkatStringWrappedNumber(String number){ //data is number
    valueWithNumber = getDoubleFrom(number) //generates result or error
        .map(this::getMaxDouble) //transform result or pass error
        .bimap(e-> e, num -> getFormattedInteger(number)) //transform result or pass error pt. 2
        .map(this::stringValue) //transform result or pass error pt. 3
        .fold(
            e -> {throw new ConfigException(e.getMessage());}, //error message...
            s -> s); //... or extract result
  }

  public String stringValue(String number) { //String returned (not Either)
    return "Value is: " + number;
  }

  private String getFormattedInteger(String number)  { //String returned (not Either)
    return String.format("%.2f", number);
  }

  private Double getMaxDouble(Double number) { //Double returned (not Either)
    return Double.max(number, 10.9);
  }

  //only one "hop" with Either!
  private Either<Exception, Double> getDoubleFrom(String number) {
    try {
      return Either.right(Double.parseDouble(number));
    } catch (NumberFormatException | NullPointerException e) {
      return Either.left(new NumberFormatException("provided number is null"));
    }
  }
}
