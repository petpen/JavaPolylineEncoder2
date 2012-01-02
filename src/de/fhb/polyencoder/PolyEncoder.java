package de.fhb.polyencoder;

import java.io.IOException;

public class PolyEncoder {

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    JerseyServer jersey = new JerseyServer();
    jersey.startServer();
    System.in.read();
    // jersey.stopServer();
    System.exit(0);
  }
}
