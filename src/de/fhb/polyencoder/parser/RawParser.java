package de.fhb.polyencoder.parser;

import java.util.StringTokenizer;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;

public class RawParser extends AbstractStringToTrackParser  implements StringToTrackParser {

  /**
   * Parses a String containing raw data. A line represents a track. A sequence
   * of points must be in the following form:
   * {@code latitude,longitude,altitude;latitude,longitude,altitude;...} If
   * altitude is missing it will be replaced with a value as described in
   * {@link AbstractStringToTrackParser#parseStringToTrack(String, TrackSeparator, PointArrayPositions)}
   * 
   * @param data
   *          String containing the raw data
   * 
   * @see AbstractStringToTrackParser#parseStringToTrack(String, TrackSeparator,
   *      PointArrayPositions)
   */
  public void parse(String data) {
    StringTokenizer st = new StringTokenizer(data, "\n");

    while (st.hasMoreTokens()) {
      String trackData = st.nextToken();
      addTrack(trackData);
    }
  }



  private void addTrack(String trackData) {
    Track trk = new Track();
    TrackSeparator sep = new TrackSeparator(";", ",");
    PointArrayPositions pos = new PointArrayPositions(0, 1, 2);

    trk = parseStringToTrack(trackData, sep, pos);

    if (trk.getPoints().size() > 0)
      tracks.add(trk);
  }
}
