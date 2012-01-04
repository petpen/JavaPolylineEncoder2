package de.fhb.polyencoder.server;

public enum OutputType {
  HTML,
  JSON,
  RAW,
  NOSUPPORT;
  
  public static OutputType test(String str) {
    try {
      return valueOf(str);
    } catch (Exception e) {
      return NOSUPPORT;
    }
  }
}
