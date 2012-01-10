package de.fhb.polyencoder.server;

/**
 * @author Martin Bormeister, Peter Pensold
 * 
 */
public enum InputType {
  GPX, KML, KMZ, RAW, NOSUPPORT;

  public static InputType test(String str) {
    try {
      return valueOf(str.toUpperCase());
    } catch (Exception e) {
      return NOSUPPORT;
    }
  }
}
