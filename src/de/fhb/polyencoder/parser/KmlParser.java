package de.fhb.polyencoder.parser;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.TrackSeparator;

public class KmlParser extends AbstractStringToTrackParser implements StringToTrackParser {

  protected static String TAG_DATA = "coordinates";
  
  /**
   * Parses a String containing points in a form as described in parameter
   * points.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Longitude,Latitude,Altitude'_' ...}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public void parse(String data) {
    TrackSeparator sep = new TrackSeparator(" ", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);

    tracks.add(parseStringToTrack(data, sep, pos));
  }
}
