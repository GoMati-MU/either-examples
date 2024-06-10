package pl.gomati.eithers.example2.bad;

import cyclops.control.Either;
import java.util.Map;
import pl.gomati.eithers.example2.cloudapi.exceptions.ResourceUnavailableException;
import pl.gomati.eithers.example2.cloudapi.CloudApi.ClientObject;
import pl.gomati.eithers.example2.cloudapi.exceptions.ConsumptionException;

public class BadCloudClientDataGrabber {

  private final ClientObject client;
  

  public BadCloudClientDataGrabber(ClientObject client) {
    this.client = client;
  }

  public Either<Exception, Map<String, String>> pollData() {
    return tryPollData();
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
