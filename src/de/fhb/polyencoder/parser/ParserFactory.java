package de.fhb.polyencoder.parser;

public class ParserFactory {
  public static StringToTrackParser buildKmlParser() {
    return new KmlParser();
  }



  public static StringToTrackParser buildGpxParser() {
    return new GpxParser();
  }



  public static StringToTrackParser buildKmzParser() {
    return new KmzParser();
  }



  public static StringToTrackParser buildRawParser() {
    return new RawParser();
  }
}
