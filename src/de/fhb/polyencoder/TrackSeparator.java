package de.fhb.polyencoder;

/**
 * Holds information for a separator for GPS coordinates. This is needed because
 * not every file with GPS coordinates has the same layout as the other ones.
 * <ul>
 * <li>Therefore it can be specified a {@code line separator} which separates
 * every block of the latitude, longitude and altitude position.</li>
 * <li>The other separator is the {@code point separator}. This separator shall
 * be used to split the three coordinates (latitude, longitude and altitude).</li>
 * </ul>
 * 
 * @author Peter Pensold
 * @version 1
 * @see PolylineEncoder#parseStringToTrack(String, TrackSeparator, PointPositions)
 */
public class TrackSeparator {
  private String lineSeparator = "";
  private String pointSeparator = "";



  public TrackSeparator(String lineSeparator, String pointSeparator) {
    setLineSeparator(lineSeparator);
    setPointSeparator(pointSeparator);
  }



  public String getLineSeparator() {
    return this.lineSeparator;
  }



  public void setLineSeparator(String separator) {
    this.lineSeparator = separator;
  }



  public String getPointSeparator() {
    return this.pointSeparator;
  }



  public void setPointSeparator(String separator) {
    this.pointSeparator = separator;
  }



  /**
   * Short for getPointSeparator()
   * 
   * @return pointSeparator
   */
  public String forPoints() {
    return getPointSeparator();
  }



  /**
   * Short for getLineSeparator()
   * 
   * @return lineSeparator
   */
  public String forLines() {
    return getLineSeparator();
  }
}
