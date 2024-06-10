package pl.gomati.eithers.example1;

import cyclops.control.Either;

//EXAMPLE 1: why not to return Either type in public API if needed
public class OtherClassWantingToReuseLogic {

  public OtherClassWantingToReuseLogic() {
    VenkatStringWrappedNumber venkatWrappedNumber = new VenkatStringWrappedNumber("11.0");
    String stringValue = venkatWrappedNumber.stringValue("someStringValue");
    // since VenkatStringWrappedNumber.stringValue(...) doesn't return Either in its public API
    // clients don't have to deal with Either (which here has no value). If client needs Either
    // he can pack it once again like this:
    // Either<Exception, String> right = Either.right(stringValue);

    StringWrappedNumberEither stringWrappedNumberEither = new StringWrappedNumberEither("11.0");
    Either<Exception, String> eitherValue = stringWrappedNumberEither.stringValue("someStringValue");
    // here since we return Either instead of contain it we have to deal with left (Exception)
    // even if it doesn't make sense in this context anymore (it's just a String, no exception will be produced)

  }
}
