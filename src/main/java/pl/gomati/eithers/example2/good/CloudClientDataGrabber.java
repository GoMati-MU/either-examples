package pl.gomati.eithers.example2.good;

import cyclops.control.Either;
import java.util.Map;
import pl.gomati.eithers.example2.cloudapi.exceptions.ConsumptionException;
import pl.gomati.eithers.example2.cloudapi.exceptions.ResourceUnavailableException;
import pl.gomati.eithers.example2.cloudapi.CloudApi.ClientObject;
import pl.gomati.exceptions.ConfigException;

public class CloudClientDataGrabber {

  private final ClientObject client;
  

  public CloudClientDataGrabber(ClientObject client) {
    this.client = client;
  }

  public Map<String, String> pollData() {
    return tryPollData().fold(e -> {
      if (e instanceof ResourceUnavailableException) {
        throw new ConfigException("Resource unavailable" + e.getMessage());
      } else {
        //retry in a while and if this won't succeed then throw:
        throw new ConfigException("Retried X times but couldn't connect to " + e.getMessage());
      }
    }, data -> data);
  }
  
  public Either<Exception, Map<String, String>> tryPollData() {
    Map<String, String> data;
    try {
      data = client.getData();
      return Either.right(data);
    } catch (ResourceUnavailableException | ConsumptionException e) {
      return Either.left(e);
    }
  }

}
