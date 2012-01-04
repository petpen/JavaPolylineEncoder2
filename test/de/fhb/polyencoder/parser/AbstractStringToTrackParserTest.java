package de.fhb.polyencoder.parser;

import static org.junit.Assert.*;
import org.junit.*;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;

public class AbstractStringToTrackParserTest {
  @Test
  public void testParseEmptyStringToTrack() {
    String pointIn = "";
    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = AbstractStringToTrackParser.parseStringToTrack(pointIn, sep, pos);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testParseStringToTrackLatLng() {
    String pointIn = "42.244814 -71.463566";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = AbstractStringToTrackParser.parseStringToTrack(pointIn, sep, pos);

    assertEquals("There must be one points in the track", 1, trkResult.getPoints().size());
    assertTrue("Latitude should be 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("Longitude should be -71.463566", -71.463566 == trkResult.getPoint(0).getLongitude());
    assertTrue("Altitude should be 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
  }



  @Test
  public void testParseStringToTrackLatLngAlt() {
    String pointIn = "42.244814 -71.463566 1.463566";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1, 2);
    Track trkResult = AbstractStringToTrackParser.parseStringToTrack(pointIn, sep, pos);

    assertEquals("There must be one points in the track", 1, trkResult.getPoints().size());
    assertTrue("Latitude should be 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("Longitude should be -71.463566", -71.463566 == trkResult.getPoint(0).getLongitude());
    assertTrue("Altitude should be 1.463566", 1.463566 == trkResult.getPoint(0).getAltitude());
  }



  @Test
  public void testParseStringToTrackLatLngIgnoreAlt() {
    String pointIn = "42.244814 -71.463566 1.463566";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = AbstractStringToTrackParser.parseStringToTrack(pointIn, sep, pos);

    assertEquals("There must be one points in the track", 1, trkResult.getPoints().size());
    assertTrue("Latitude should be 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("Longitude should be -71.463566", -71.463566 == trkResult.getPoint(0).getLongitude());
    assertTrue("Altitude should be 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
  }



  @Test
  public void testParseStringWithMultiplePointsToTrackLatLng() {
    String pointsIn = "42.244814 -71.463566\n42.240618 -71.464372";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = AbstractStringToTrackParser.parseStringToTrack(pointsIn, sep, pos);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an latitude of 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("The first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an latitude of 42.240618", 42.240618 == trkResult.getPoint(1).getLatitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testParseStringWithMultiplePointsToTrackLatLngAlt() {
    String pointsIn = "42.244814 -71.463566 1.463566\n42.240618 -71.464372 0.0";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1, 2);
    Track trkResult = AbstractStringToTrackParser.parseStringToTrack(pointsIn, sep, pos);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 1.463566", 1.463566 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
  }
}
