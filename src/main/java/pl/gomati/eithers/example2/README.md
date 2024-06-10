# EXAMPLE 2 - EXCEPTION BUBBLING

## Use Case:
We create a "Connector" that has to authorize with cloud data source from given properties (`Map<String, String>`). 
Using the same properties it has to create mappings from some of the fields to another. 
It then has to connect to it and start receiving data.

### We want to:
1. Initialize the Authorization mechanism in order to create connection (it can be user/password or connection string)
2. Initialize "connection" once authorization mechanism is instantiated
3. Initialize the mappings from properties (it's a `Map<String, String>` of input field name to output field name)
4. Start receiving data
5. Get the output via `poll()` method.

### What can go wrong?
- user can forget to provide Authorization method
- user can forget to provide authorization details (user/pass or connection String)
- Authorization can not succeed (e.g. user+pass no longer valid) and get server error
- Authorization can succeed but user may have provided bad resourceUrl and get connection error
- Mapping can have nulls (MappingException) or duplicates (DuplicatedMappingException)
- Something can go wrong when receiving data (`ConsumptionException`)

### Cloud API and Solutions
Cloud API is in `cloudapi` subpackage. Two solutions: good (in `good` subpackage) and bad (`bad` subpackage) use it.

## What can we do?
- Prefer to return values instead of `Eithers` in public methods as client can always wrap them in `Either` if needed. 
Leaving `Either` as returned type can confuse user that he needs to handle more scenarios.
- Avoid bubbling up Exceptions to the top level. Deal with Exception once we have all the information (e.g. by throwing 
ConfigException) will most likely simplify the logic. Have only one "hop" (function call) with `Either` unless we have 
a strong reason to do otherwise (rule of thumb: max two hops).
