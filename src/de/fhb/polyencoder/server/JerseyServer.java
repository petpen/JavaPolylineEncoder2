package de.fhb.polyencoder.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

/**
 * @author Martin Bormeister
 * 
 */
public class JerseyServer {
  private SelectorThread threadSelector;
  private final String baseUri = "http://localhost:9998/";
  private final Map<String, String> initParams = new HashMap<String, String>();



  public JerseyServer() {
    initParams.put("com.sun.jersey.config.property.packages", "de.fhb.polyencoder.server.resources");
  }



  public void startServer() throws IOException {
    System.out.println("Run Jersey...");
    threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
    System.out.println("Run Server on: " + baseUri);
    System.out.println("Jersey started. WADL available under /application.wadl\n" + "Push 'Return' to terminate Server...");
  }



  public void stopServer() {
    System.out.println("Terminate Jersey...");
    threadSelector.stopEndpoint();
    System.out.println("Jersey terminated");
  }



  public boolean isRunnig() {
    return threadSelector.isRunning();
  }
}
