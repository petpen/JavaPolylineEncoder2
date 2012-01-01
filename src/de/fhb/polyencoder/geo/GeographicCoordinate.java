package de.fhb.polyencoder.geo;

public enum GeographicCoordinate {
  LATITUDE, LONGITUDE, ALTITUDE;

  public static boolean isValidLatitude(double value) {
    return (-90 <= value && value <= 90);
  }



  public static boolean isInvalidLatitude(double value) {
    return !isValidLatitude(value);
  }



  public static boolean isValidLongitude(double value) {
    return (-180 < value && value <= 180);
  }



  public static boolean isInvalidLongitude(double value) {
    return !isValidLongitude(value);
  }

}
