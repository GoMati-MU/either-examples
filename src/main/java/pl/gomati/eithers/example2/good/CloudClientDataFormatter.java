package pl.gomati.eithers.example2.good;

import cyclops.control.Either;
import java.util.Map;
import java.util.function.Function;
import pl.gomati.exceptions.ConfigException;
import pl.gomati.exceptions.DuplicatedMappingException;
import pl.gomati.exceptions.MappingException;

public class CloudClientDataFormatter {
  Function<Map<String, String>, Map<String, String>> translationFunction;

  public CloudClientDataFormatter(Map<String, String> props) {
    translationFunction = prepareTranslationFunction(props).fold(
        e -> {throw new ConfigException("Resource unavailable" + e.getMessage());},
        func -> func);
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

  public Map<String, String> formatWithMappings(Map<String, String> data) {
    return translationFunction.apply(data);
  }
}
