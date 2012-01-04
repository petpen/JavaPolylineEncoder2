package de.fhb.polyencoder.parser;

import de.fhb.polyencoder.server.InputType;

public class ParserFactory {
  public static StringToTrackParser buildParser(InputType type) {
    switch (type) {
    case GPX:
      return buildGpxParser();
    case KML:
      return buildKmlParser();
    case KMZ:
      return buildKmzParser();
    case RAW:
      return buildRawParser();
    default:
      return null;
    }
  }
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
