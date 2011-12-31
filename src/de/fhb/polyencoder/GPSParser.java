package de.fhb.polyencoder;

import java.util.StringTokenizer;

/**
 * This class was part of {@link PolylineEncoder}.
 * 
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
 * @author Peter Pensold
 * @version 0.5
 */
public class GPSParser {
  /**
   * Parses a String containing points in a form as described in param points.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Longitude,Latitude,Altitude'_' ...}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public static Track kmlLineStringToTrack(String points) {
    TrackSeparator sep = new TrackSeparator(" ", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);

    return parseStringToTrack(points, sep, pos);
  }



  /**
   * Parses a String containing points in a form as described in param points.
   * Google can't show Altitude, but its in some GPS/GPX Files.
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
    System.out.println("pointsWithAltitudeToTrack");
    TrackSeparator sep = new TrackSeparator("\n", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);

    return parseStringToTrack(points, sep, pos);
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

    return parseStringToTrack(points, sep, pos);
  }



  /**
   * Parses a String to a Track. If one point is defined by two coordinates the
   * missing coordinate will be the altitude. The altitude will be set to 0.0
   * 
   * @param points
   * @param separator
   * @param positions
   * 
   * @return the parsed track
   * 
   * @see TrackSeparator
   * @see PointArrayPositions
   */
  public static Track parseStringToTrack(String points, TrackSeparator separator, PointArrayPositions positions) {
    Track trk = new Track();
    StringTokenizer st = new StringTokenizer(points, separator.forLines());

    while (st.hasMoreTokens()) {
      String[] pointStrings = st.nextToken().split(separator.forPoints());

      switch (pointStrings.length) {
        case 3:
          trk.addPoint(new Trackpoint(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), new Double(pointStrings[positions.alt()])));
          break;

        case 2:
          trk.addPoint(new Trackpoint(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), 0.0));
          break;
      }
    }

    return trk;
  }
}
