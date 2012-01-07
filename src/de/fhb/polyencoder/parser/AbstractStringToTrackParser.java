package de.fhb.polyencoder.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;
import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.geo.GeographicLocation;

public abstract class AbstractStringToTrackParser {
  protected List<Track> tracks = new ArrayList<Track>();


  public void parseFile(String fileName) {
    String fileContent = Util.readFile(fileName);
    parse(fileContent);
  }



  public abstract void parse(String data);



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
          trk.addPoint(new GeographicLocation(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), altitude));
          break;

        case 2:
          trk.addPoint(new GeographicLocation(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), 0.0));
          break;
      }
    }

    return trk;
  }



  public List<Track> getTracks() {
    return tracks;
  }
}
