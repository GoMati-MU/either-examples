# EXAMPLE 1 - LOGIC REUSE

## Use Case:
We have two implementations of Number Wrappers (`StringWrappedNumberEither` and `VenkatStringWrappedNumber`. They:
- take String as parameter (should be floting-point number)
- tries to parse Double out of it
- gets Max out of it and 10.9
- Picks last 2 decimal places then concatenates "Value is:" string in front of it.

There's also another class called `OtherClassWantingToReuseLogic`. It shows differences of two approaches and why 
using `Either` as returned type in method may be problematic.

## What can we do?
- Prefer to return values instead of `Eithers` in public methods as client can always wrap them in `Either` if needed.
  Leaving `Either` as returned type can confuse user that he needs to handle more scenarios.
- Looking at the Number Wrappers code only `getDoubleFrom` method needs Either (as it parses double and can face the exception)