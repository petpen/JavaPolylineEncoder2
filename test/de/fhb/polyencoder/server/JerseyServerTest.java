package de.fhb.polyencoder.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Martin Bormeister
 * 
 */
public class JerseyServerTest {

  private JerseyServer server;



  public JerseyServerTest() {
    server = new JerseyServer();
  }



  @Test
  public void startStop() throws IOException {
    server.startServer();
    assertTrue("Server should runnig.", server.isRunnig());
    server.stopServer();
    assertFalse("Server should not runnig.", server.isRunnig());
  }
}
