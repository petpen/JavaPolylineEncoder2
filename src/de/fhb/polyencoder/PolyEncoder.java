package de.fhb.polyencoder;

import java.io.IOException;

public class PolyEncoder {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    System.out.println(work("yes"));
    RunServer.RunServer();
  }

  static String work(String str) {
    return str;
  }
}
