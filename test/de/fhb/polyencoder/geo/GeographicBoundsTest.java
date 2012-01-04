package de.fhb.polyencoder.geo;

import static org.junit.Assert.*;

import org.junit.Test;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.GeographicLocation;

public class GeographicBoundsTest {
  @Test
  public void testBoundsOnePoint() {
    Track trk = new Track();
    trk.addPoint(new GeographicLocation(15.0, 10.0, 0.0));

    GeographicBounds bounds = new GeographicBounds(trk);

    assertTrue("maxlat must be 15.0", 15.0 == bounds.getMaxLat());
    assertTrue("minlat must be 15.0", 15.0 == bounds.getMinLat());

    assertTrue("maxlng must be 10.0", 10.0 == bounds.getMaxLng());
    assertTrue("minlng must be 10.0", 10.0 == bounds.getMinLng());

    assertTrue("maxalt must be 0.0", 0.0 == bounds.getMaxAlt());
    assertTrue("minalt must be 0.0", 0.0 == bounds.getMinAlt());
  }



  @Test
  public void testBoundsMorePoint() {
    Track trk = new Track();
    trk.addPoint(new GeographicLocation(15.0, 10.0, 3.0));
    trk.addPoint(new GeographicLocation(50.0, 5.0, 2.0));
    trk.addPoint(new GeographicLocation(80.0, 3.0, 10.0));

    GeographicBounds bounds = new GeographicBounds(trk);

    assertTrue("maxlat must be 80.0", 80.0 == bounds.getMaxLat());
    assertTrue("minlat must be 15.0", 15.0 == bounds.getMinLat());

    assertTrue("maxlng must be 10.0", 10.0 == bounds.getMaxLng());
    assertTrue("minlng must be 3.0", 3.0 == bounds.getMinLng());

    assertTrue("maxalt must be 10.0", 10.0 == bounds.getMaxAlt());
    assertTrue("minalt must be 2.0", 2.0 == bounds.getMinAlt());
  }



  @Test
  public void testCenter() {
    Track trk = new Track();
    trk.addPoint(new GeographicLocation(20.0, 0.0, 5.0));
    trk.addPoint(new GeographicLocation(40.0, 10.0, 10.0));

    GeographicBounds bounds = new GeographicBounds(trk);

    assertTrue("CenterLat must be 30.0", 30.0 == bounds.getCenterLat());
    assertTrue("CenterLng must be 5.0", 5.0 == bounds.getCenterLng());
    assertTrue("CenterAlt must be 7.5", 7.5 == bounds.getCenterAlt());
  }
}
