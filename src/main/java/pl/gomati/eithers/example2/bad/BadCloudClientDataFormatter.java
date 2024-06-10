package pl.gomati.eithers.example2.bad;

import cyclops.control.Either;
import java.util.Map;
import java.util.function.Function;
import pl.gomati.exceptions.DuplicatedMappingException;
import pl.gomati.exceptions.MappingException;

public class BadCloudClientDataFormatter {
  Either<Exception, Function<Map<String, String>, Map<String, String>>> translationFunction;

  public BadCloudClientDataFormatter(Map<String, String> props) {
    translationFunction = prepareTranslationFunction(props);
  }

  private Either<Exception, Function<Map<String, String>, Map<String, String>>> prepareTranslationFunction(
      Map<String, String> props) {
    try {
      checkForMappingDuplicates(props);
      checkForNullMappings(props);
    } catch (MappingException | DuplicatedMappingException e) {
      return Either.left(e);
    }
    return Either.right(Function.identity()); //this can return mapping function instead
  }

  private void checkForNullMappings(Map<String, String> props) throws MappingException {
    throw new MappingException("null mapping");
  }

  private void checkForMappingDuplicates(Map<String, String> props) throws DuplicatedMappingException {
    throw new DuplicatedMappingException("duplicates: ...");
  }

  public Either<Exception, Map<String, String>> formatWithMappings(Either<Exception, Map<String, String>> dataEither) {
    return translationFunction.flatMap(func -> dataEither.bimap(ex -> ex, func));
  }
}
