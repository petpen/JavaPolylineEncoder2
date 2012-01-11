package de.fhb.polyencoder.geo;

/**
 * 
 * @author Peter Pensold
 * @version 1
 */
public enum GeographicCoordinate {
  LATITUDE, LONGITUDE, ALTITUDE;

  public static boolean isValidLatitude(double value) {
    return (-90d <= value && value <= 90d);
  }



  public static boolean isInvalidLatitude(double value) {
    return !isValidLatitude(value);
  }



  public static boolean isValidLongitude(double value) {
    return (-180d < value && value <= 180d);
  }



  public static boolean isInvalidLongitude(double value) {
    return !isValidLongitude(value);
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
  public static int toInt(double value, GeographicCoordinate coordinate) throws CoordinateOutOfRangeException {
    switch (coordinate) {
      case LATITUDE:
        if (isInvalidLatitude(value))
          throw new CoordinateOutOfRangeException("The latitude must be in a range of -90 <= lat <= 90.");
        break;
      case LONGITUDE:
        if (isInvalidLongitude(value))
          throw new CoordinateOutOfRangeException("The longitude must be in a range of -180 < lng <= 180.");

        double smallestLongitudeForFlooring = -179.999990;
        
        if(value < smallestLongitudeForFlooring)
          value = smallestLongitudeForFlooring;
        
        break;
    }

    return (int) Math.floor(value*1e5);
  }
}
