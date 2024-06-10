package pl.gomati.eithers.example2.cloudapi;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import pl.gomati.eithers.example2.cloudapi.exceptions.AuthException;
import pl.gomati.eithers.example2.cloudapi.exceptions.ConsumptionException;
import pl.gomati.eithers.example2.cloudapi.exceptions.CredentialsException;
import pl.gomati.eithers.example2.cloudapi.exceptions.LoginException;
import pl.gomati.eithers.example2.cloudapi.exceptions.ResourceUnavailableException;

public class CloudApi {

  public static class LoginService {
    public static ClientObject login(AuthorizationToken authObject) throws LoginException {
      throw new LoginException("bad connectionString:" + authObject.loginData);
    }
  }

  @Getter
  @Setter
  public static class AuthorizationService {
    public enum AuthMethod { USER_PASS, STRING }

    private final AuthMethod authMethod;
    String user;
    String pass;
    String connectionString;

    public AuthorizationService(AuthMethod authMethod) throws AuthException {
      if (authMethod == null) {
        throw new AuthException("invalid authmethod");
      }
      this.authMethod = authMethod;
    }

    public AuthorizationToken makeAuthToken() throws CredentialsException {
      if (authMethod == AuthMethod.USER_PASS ) {
        if (user == null || pass == null) {
          throw new CredentialsException("User or pass null");
        }
        return new AuthorizationToken(user + pass);
      } else {
        if (connectionString == null) {
          throw new CredentialsException("Connection String is null");
        }
        return new AuthorizationToken(connectionString);
      }
    }

  }

  //received if auth properly formatted
  public static class AuthorizationToken {
    public String loginData;
    AuthorizationToken(String loginData) {
      this.loginData = loginData;
    }
  }

  //received if successfully login with AuthorizationToken
  public interface ClientObject {
    Map<String, String> getData() throws ConsumptionException, ResourceUnavailableException;
  }

}



