package de.fhb.polyencoder.geo;

import java.util.StringTokenizer;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;
import de.fhb.polyencoder.Trackpoint;

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
  public static Track kmlLineStringToTrack(String points) {
    TrackSeparator sep = new TrackSeparator(" ", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);

    return parseStringToTrack(points, sep, pos);
  }



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
   * missing coordinate will be the altitude. The altitude will be set to 0.0 in
   * the case it is not set or not defined in parameter positions.
   * 
   * @param points
   *          String which contains points to parse
   * @param separator
   *          {@link TrackSeparator} for line and point coordinates
   * @param positions
   *          {@link PointArrayPositions} for positions of the parsed
   *          coordinates
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
          double altitude = (positions.getAltitudePos() == PointArrayPositions.EMTPY_COORDINATE) ? 0.0 : new Double(pointStrings[positions.alt()]);
          trk.addPoint(new Trackpoint(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), altitude));
          break;

        case 2:
          trk.addPoint(new Trackpoint(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), 0.0));
          break;
      }
    }

    return trk;
  }



  /**
   * Swaps a geographic coordinate from double to integer. As defined by the
   * geographic coordinate system the latitude (-90 <= lat <= 90) and the
   * longitude (-180 < lng <= 180) have ranges as described in the braces. After
   * swapping the value will be multiplied by 1e5 (100000) and floored to match
   * into an integer.
   * 
   * @param value
   * @param coordinate
   * 
   * @throws CoordinateOutOfRangeException
   * 
   * @return
   */
  public static int floor1e5(double value, GeographicCoordinate coordinate) throws CoordinateOutOfRangeException {
    switch (coordinate) {
      case LATITUDE:
        if (GeographicCoordinate.isInvalidLatitude(value))
          throw new CoordinateOutOfRangeException("The latitude must be in a range of -90 <= lat <= 90.");
        break;
      case LONGITUDE:
        if (GeographicCoordinate.isInvalidLongitude(value))
          throw new CoordinateOutOfRangeException("The longitude must be in a range of -180 < lng <= 180.");

        double smallestLongitudeForFlooring = -179.999990;
        
        if(value < smallestLongitudeForFlooring)
          value = smallestLongitudeForFlooring;
        
        break;
    }

    return (int) Math.floor(value*1e5);
  }
}
