package de.fhb.polyencoder.parser;

import org.w3c.dom.Document;

import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.geo.GeographicPositionParser;

public class GpxParser extends AbstractStringToTrackParser implements StringToTrackParser {
  private static String TRACK = "trk";
  private static String TRACKPOINT = "trkpt";
  
  private static String ROUTE = "rte";  
  private static String ROUTEPOINT = "rtept";

  private static String WAYPOINT = "wpt";
  
  public void parse(String data) {
    Document dom = Util.parseXMLToDocument(data);
    
    
    
    tracks.add(GeographicPositionParser.pointsToTrack(data));
  }
}
