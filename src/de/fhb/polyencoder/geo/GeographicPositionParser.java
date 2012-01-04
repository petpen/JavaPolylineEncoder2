package de.fhb.polyencoder.geo;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;
import de.fhb.polyencoder.parser.AbstractStringToTrackParser;

/**
 * This class was part of {@link PolylineEncoder}. It is used to parse points
 * placed in the geographic coordinate system. They are represented by
 * longitude, latitude and/or altitude
 * 
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
 * @author Peter Pensold
 * @version 0.5
 */
public class GeographicPositionParser {

  /**
   * Parses a String containing points in a form as described in parameter
   * points. Google can't show Altitude, but its in some GPS/GPX Files.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Longitude,Latitude,Altitude\n}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public static Track pointsWithAltitudeToTrack(String points) {
    TrackSeparator sep = new TrackSeparator("\n", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);

    return AbstractStringToTrackParser.parseStringToTrack(points, sep, pos);
  }



  /**
   * Parses a String containing points in a form as described in parameter
   * points. The Altitude will be set to 0.0.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Latitude, Longitude\n}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public static Track pointsToTrack(String points) {
    TrackSeparator sep = new TrackSeparator("\n", ", ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);

    return AbstractStringToTrackParser.parseStringToTrack(points, sep, pos);
  }
}
