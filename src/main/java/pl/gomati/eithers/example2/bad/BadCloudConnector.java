package pl.gomati.eithers.example2.bad;

import java.util.Map;
import pl.gomati.exceptions.ConfigException;

public class BadCloudConnector {

  BadCloudClientDataGrabber dataGrabber;
  BadCloudClientDataFormatter formatter;

  public BadCloudConnector(Map<String, String> props) {

    BadCloudClientFacade badCloudClientFacade = new BadCloudClientFacade(props);

    formatter = new BadCloudClientDataFormatter(props);
    dataGrabber = new BadCloudClientDataGrabber(badCloudClientFacade.getClientObject().fold(
        e -> {throw new ConfigException(e);}, // do we even know what is going on? Plus it's boilerplate here.
        client -> client
    ));
  }

  public Map<String, String> poll() {
    return formatter.formatWithMappings(dataGrabber.pollData()).fold(
        exception -> {throw new ConfigException("Something bad happened but I don't know what anymore");},
        dataMap -> dataMap
    );
  }

}
