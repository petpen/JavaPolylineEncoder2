package de.fhb.polyencoder.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class GeographicCoordinateTest {

  @Test
  public void testToIntValidLatitudes() {
    try {
      assertEquals("The latitude 0.0 must be 0 after the method call", 0, GeographicCoordinate.toInt(0.0, GeographicCoordinate.LATITUDE));
      assertEquals("The latitude 90.0 must be 9000000 after the method call", 9000000, GeographicCoordinate.toInt(90.0, GeographicCoordinate.LATITUDE));
      assertEquals("The latitude -90.0 must be -9000000 after the method call", -9000000, GeographicCoordinate.toInt(-90.0, GeographicCoordinate.LATITUDE));
    } catch (CoordinateOutOfRangeException e) {
      fail(e.getMessage());
    }
  }



  @Test(expected = CoordinateOutOfRangeException.class)
  public void testToIntAboveRangeLatitude() throws CoordinateOutOfRangeException {
    GeographicCoordinate.toInt(90.000001, GeographicCoordinate.LATITUDE);
  }



  @Test(expected = CoordinateOutOfRangeException.class)
  public void testToIntUnderRangeLatitude() throws CoordinateOutOfRangeException {
    GeographicCoordinate.toInt(-90.000001, GeographicCoordinate.LATITUDE);
  }



  @Test
  public void testToIntValidLongitudes() {
    try {
      assertEquals("The latitude 0.0 must be 0 after the method call", 0, GeographicCoordinate.toInt(0.0, GeographicCoordinate.LONGITUDE));
      assertEquals("The latitude 180.0 must be 18000000 after the method call", 18000000, GeographicCoordinate.toInt(180.0, GeographicCoordinate.LONGITUDE));
      assertEquals("The latitude -179.999990 must be -17999999 after the method call", -17999999, GeographicCoordinate.toInt(-179.999990, GeographicCoordinate.LONGITUDE));
      assertEquals("The latitude -179.999999 must be -17999999 after the method call", -17999999, GeographicCoordinate.toInt(-179.999999, GeographicCoordinate.LONGITUDE));
    } catch (CoordinateOutOfRangeException e) {
      fail(e.getMessage());
    }
  }



  @Test(expected = CoordinateOutOfRangeException.class)
  public void testToIntAboveRangeLongitude() throws CoordinateOutOfRangeException {
    GeographicCoordinate.toInt(180.000001, GeographicCoordinate.LONGITUDE);
  }



  @Test(expected = CoordinateOutOfRangeException.class)
  public void testToIntUnderRangeLongitude() throws CoordinateOutOfRangeException {
    GeographicCoordinate.toInt(-180.0, GeographicCoordinate.LONGITUDE);
  }
}
