package pl.gomati.eithers.example2.good;

import java.util.Map;

public class CloudConnector {

  CloudClientDataGrabber dataGrabber;
  CloudClientDataFormatter formatter;

  public CloudConnector(Map<String, String> props) {

    CloudClientFacade cloudClientFacade = new CloudClientFacade(props);

    formatter = new CloudClientDataFormatter(props);
    dataGrabber = new CloudClientDataGrabber(cloudClientFacade.getClientObject());
  }

  public Map<String, String> poll() {
    return formatter.formatWithMappings(dataGrabber.pollData()); //clean!
  }

}
