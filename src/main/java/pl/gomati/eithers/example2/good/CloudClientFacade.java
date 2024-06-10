package pl.gomati.eithers.example2.good;

import cyclops.control.Either;
import java.util.Map;
import pl.gomati.eithers.example2.cloudapi.exceptions.AuthException;
import pl.gomati.eithers.example2.cloudapi.exceptions.CredentialsException;
import pl.gomati.eithers.example2.cloudapi.exceptions.LoginException;
import pl.gomati.eithers.example2.cloudapi.CloudApi.AuthorizationService;
import pl.gomati.eithers.example2.cloudapi.CloudApi.AuthorizationService.AuthMethod;
import pl.gomati.eithers.example2.cloudapi.CloudApi.AuthorizationToken;
import pl.gomati.eithers.example2.cloudapi.CloudApi.ClientObject;
import pl.gomati.eithers.example2.cloudapi.CloudApi.LoginService;
import pl.gomati.exceptions.ConfigException;

public class CloudClientFacade {
  AuthorizationToken authTokenFromApi;

  public CloudClientFacade(Map<String, String> props) {
    authTokenFromApi = getAuthTokenFromApi(props).fold(
        e -> {throw new ConfigException(e);},
        token -> token
    );
  }

  public Either<Exception, AuthorizationToken> getAuthTokenFromApi(Map<String, String> props){
    AuthMethod authMethod = AuthMethod.valueOf(props.get("auth.method"));
    AuthorizationService authorizationService;

    try {
      authorizationService = new AuthorizationService(authMethod);
    } catch (AuthException e) {
      return Either.left(e);
    }

    if (authMethod == AuthMethod.USER_PASS) {
      authorizationService.setUser(props.get("user"));
      authorizationService.setPass(props.get("pass"));
    } else {
      authorizationService.setConnectionString(props.get("connection.string"));
    }

    try {
      return Either.right(authorizationService.makeAuthToken());
    } catch (CredentialsException e) {
      return Either.left(e);
    }
  }

  ClientObject getClientObject() {
    return tryGetClientObject().fold(
        e -> { throw new ConfigException("Couldn't login");}, //this exception is nasty, has user and pass!
        client -> client
    );
  }

  private Either<LoginException, ClientObject> tryGetClientObject() {
    try {
      return Either.right(LoginService.login(authTokenFromApi));
    } catch (LoginException e) {
      return Either.left(e);
    }
  }
}
