package de.fhb.polyencoder.parser;

import de.fhb.polyencoder.geo.GeographicPositionParser;

public class GpxParser extends AbstractStringToTrackParser implements StringToTrackParser {

  public void parse(String data) {
    //TODO parse XML of a gpx file
    trk =  GeographicPositionParser.pointsToTrack(data);
  }
}
