package de.fhb.polyencoder.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class JerseyServer {
  private SelectorThread threadSelector;
  private final String baseUri = "http://localhost:9998/";
  private final Map<String, String> initParams = new HashMap<String, String>();



  public JerseyServer() {
    initParams.put("com.sun.jersey.config.property.packages", "de.fhb.polyencoder.server.resources");
  }



  public void startServer() throws IOException {
    System.out.println("Starte Jersey...");
    threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
    System.out.println("Run Server on: " + baseUri);
    System.out.println("Jersey gestartet WADL erreichbar unter /application.wadl\n" + "Drücke Enter zum stoppen des Servers...");
  }



  public void stopServer() {
    System.out.println("Stoppe Jersey...");
    threadSelector.stopEndpoint();
    System.out.println("Jersey gestoppt");
  }
}
