package de.fhb.polyencoder.server;

/**
 * @author Martin Bormeister
 * @author Peter Pensold
 * 
 */
public enum OutputType {
  HTML, JSON, RAW, XML, NOSUPPORT;

  public static OutputType test(String str) {
    try {
      return valueOf(str.toUpperCase());
    } catch (Exception e) {
      return NOSUPPORT;
    }
  }
}
