package pl.gomati.eithers.example2.bad;

import cyclops.control.Either;
import java.util.Map;
import pl.gomati.eithers.example2.cloudapi.CloudApi.AuthorizationService;
import pl.gomati.eithers.example2.cloudapi.CloudApi.AuthorizationService.AuthMethod;
import pl.gomati.eithers.example2.cloudapi.CloudApi.AuthorizationToken;
import pl.gomati.eithers.example2.cloudapi.CloudApi.ClientObject;
import pl.gomati.eithers.example2.cloudapi.CloudApi.LoginService;
import pl.gomati.eithers.example2.cloudapi.exceptions.AuthException;
import pl.gomati.eithers.example2.cloudapi.exceptions.CredentialsException;
import pl.gomati.eithers.example2.cloudapi.exceptions.LoginException;

public class BadCloudClientFacade {
  Either<Exception, AuthorizationToken> authTokenFromApi;

  public BadCloudClientFacade(Map<String, String> props) {
    authTokenFromApi = getAuthTokenFromApi(props);
  }

  public Either<Exception, AuthorizationToken> getAuthTokenFromApi(Map<String, String> props){
    AuthMethod authMethod = AuthMethod.valueOf(props.get("auth.method"));
    Either<Exception, AuthorizationService> authorizationServiceEither;

    try {
      authorizationServiceEither = Either.right(new AuthorizationService(authMethod));
    } catch (AuthException e) {
      return Either.left(e);
    }

    return authorizationServiceEither.flatMap(authService -> {
      if (authMethod == AuthMethod.USER_PASS) {
        authService.setUser(props.get("user"));
        authService.setPass(props.get("pass"));
      } else {
        authService.setConnectionString(props.get("connection.string"));
      }
      try {
        return Either.right(authService.makeAuthToken());
      } catch (CredentialsException e) {
        return Either.left(e);
      }
    });
  }

  Either<Exception, ClientObject> getClientObject() {
    return tryGetClientObject();
  }

  private Either<Exception, ClientObject> tryGetClientObject() {
      return authTokenFromApi.flatMap(token -> {
        try {
          return Either.right(LoginService.login(token));
        } catch (LoginException e) {
          return Either.left(e);
        }
      });
  }
}
