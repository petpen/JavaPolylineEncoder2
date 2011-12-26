package de.fhb.polyencoder;

/**
 * Holds information about the positions of a GPS Point. This point is generally
 * presented by three values (latitude, longitude and altitude). To simplify
 * coding we use this class. If you split a String which contains all the values
 * or at least two you can use this class to define which value can be found on
 * which position inside this array.
 * 
 * @author Peter Pensold
 * @version 0.5
 * @see PolylineEncoder#parseStringToTrack(String, TrackSeparator,
 *      PointArrayPositions)
 */
public class PointArrayPositions {
  private int altitudePos = 2;
  private int latitudePos = 0;
  private int longitudePos = 1;



  /**
   * Creates an instance of PointArrayPositions with the following default
   * values:
   * <ul>
   * <li>Latitude = 0</li>
   * <li>Longitude = 1</li>
   * <li>Altitude = 2</li>
   * </ul>
   */
  public PointArrayPositions() {
    this(0, 1, 2);
  }



  /**
   * Creates an instance of PointArrayPositions with the given positions for
   * latitude and longitude. Altitude will be set to 0 to prevent
   * {@link ArrayIndexOutOfBoundsException}.
   * 
   * @param lat
   *          Latitude of a point
   * @param lng
   *          Longitude of a point
   */
  public PointArrayPositions(int lat, int lng) {
    this(lat, lng, 0);
  }



  /**
   * Creates an instance of PointArrayPositions with the given positions for
   * latitude, longitude and altitude.
   * 
   * @param lat
   *          Latitude of a point
   * @param lng
   *          Longitude of a point
   * @param alt
   *          Altitude of a point
   */
  public PointArrayPositions(int lat, int lng, int alt) {
    setLatitudePos(lat);
    setLongitudePos(lng);
    setAltitudePos(alt);
  }



  public int getAltitudePos() {
    return this.altitudePos;
  }



  private void setAltitudePos(int alt) {
    this.altitudePos = alt;
  }



  public int getLatitudePos() {
    return this.latitudePos;
  }



  private void setLatitudePos(int lat) {
    this.latitudePos = lat;
  }



  public int getLongitudePos() {
    return this.longitudePos;
  }



  private void setLongitudePos(int lng) {
    this.longitudePos = lng;
  }



  /**
   * Short for getAltitudePos()
   * 
   * @return altitudePos
   */
  public int alt() {
    return getAltitudePos();
  }



  /**
   * Short for getLatitudePos()
   * 
   * @return latitudePos
   */
  public int lat() {
    return getLatitudePos();
  }



  /**
   * Short for getLongitudePos()
   * 
   * @return longitudePos
   */
  public int lng() {
    return getLongitudePos();
  }
}
