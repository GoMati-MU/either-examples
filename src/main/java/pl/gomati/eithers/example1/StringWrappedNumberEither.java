package pl.gomati.eithers.example1;

import static java.util.function.UnaryOperator.identity;

import cyclops.control.Either;
import cyclops.control.Try;
import pl.gomati.exceptions.ConfigException;

// Fewer gymnastics as we don't have to consider both sides
// of the Either all the time, only if you need to transform
// it.
public class StringWrappedNumberEither {
  final String valueWithNumber;

  public StringWrappedNumberEither(String number){
    valueWithNumber = stringValue(number).fold(
        e -> { throw new ConfigException(e.getMessage()); }, //finally dealt!
        identity());
  }

  public Either<Exception, String> stringValue(String number) {
    return getFormattedInteger(number)
        .map(num -> "Value is:" + num);
  }

  private Either<Exception, String> getFormattedInteger(String number) {
    return getMaxDouble(number)
        .map( n -> String.format("%.2f", n));
  }

  private Either<Exception, Double> getMaxDouble(String number) {
    return getDoubleFrom(number)
        .map( n -> Double.max(n, 10.9d));
  }

  private Either<Exception, Double> getDoubleFrom(String number) {
    return Try.withCatch(
        () -> Double.parseDouble(number),
        Exception.class
    ).toEither();
  }
}