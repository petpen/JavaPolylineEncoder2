package de.fhb.polyencoder;

import java.io.IOException;

import de.fhb.polyencoder.server.JerseyServer;

/**
 * @author Martin Bormeister
 * @author Peter Pensold
 * 
 */
public class JavaPolylineEncoder2 {

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    JerseyServer jersey = new JerseyServer();
    jersey.startServer();
    System.in.read();
    jersey.stopServer();
    System.exit(0);
  }
}
