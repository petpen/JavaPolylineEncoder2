package de.fhb.polyencoder.geo;

import static org.junit.Assert.*;
import org.junit.*;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;
import de.fhb.polyencoder.geo.GeographicPositionParser;

public class GeographicPositionParserTest {
  @Test
  public void testParseEmptyStringToTrack() {
    String pointIn = "";
    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = GeographicPositionParser.parseStringToTrack(pointIn, sep, pos);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testParseStringToTrackLatLng() {
    String pointIn = "42.244814 -71.463566";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = GeographicPositionParser.parseStringToTrack(pointIn, sep, pos);

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
    Track trkResult = GeographicPositionParser.parseStringToTrack(pointIn, sep, pos);

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
    Track trkResult = GeographicPositionParser.parseStringToTrack(pointIn, sep, pos);

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
    Track trkResult = GeographicPositionParser.parseStringToTrack(pointsIn, sep, pos);

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
    Track trkResult = GeographicPositionParser.parseStringToTrack(pointsIn, sep, pos);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 1.463566", 1.463566 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testParseKmlLineToTrack() {
    String line = "42.244814,-71.463566,0.0 42.240618,-71.464372,1.463566";
    Track trkResult = GeographicPositionParser.kmlLineStringToTrack(line);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 1.463566", 1.463566 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testParseKmlLineMixedPointsToTrack() {
    String line = "42.244814,-71.463566,1.6546 42.240618,-71.464372 43.240618,-69.664352";
    Track trkResult = GeographicPositionParser.kmlLineStringToTrack(line);

    assertEquals("There must be three points in the track", 3, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 1.6546", 1.6546 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
    assertTrue("The third point must have an altitude of 0.0", 0.0 == trkResult.getPoint(2).getAltitude());
  }



  @Test
  public void testNoPointsInKmlLineToTrack() {
    String line = "42.244814 -71.463566";
    Track trkResult = GeographicPositionParser.kmlLineStringToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testInvalidKmlLineToTrack() {
    String line = "42.244814-71.463566";
    Track trkResult = GeographicPositionParser.kmlLineStringToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testEmptyKmlLineToTrack() {
    String line = "";
    Track trkResult = GeographicPositionParser.kmlLineStringToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testPointsWithAltitudeToTrack() {
    String line = "42.244814,-71.463566,0.0\n42.240618,-71.464372,1.463566";
    Track trkResult = GeographicPositionParser.pointsWithAltitudeToTrack(line);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 1.463566", 1.463566 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testMixedPointsInPointsWithAltitudeToTrack() {
    String line = "42.244814,-71.463566,1.6546\n42.240618,-71.464372\n43.240618,-69.664352";
    Track trkResult = GeographicPositionParser.pointsWithAltitudeToTrack(line);

    assertEquals("There must be three points in the track", 3, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 1.6546", 1.6546 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
    assertTrue("The third point must have an altitude of 0.0", 0.0 == trkResult.getPoint(2).getAltitude());
  }



  @Test
  public void testNoPointsInPointsWithAltitudeToTrack() {
    String line = "42.244814\n-71.463566";
    Track trkResult = GeographicPositionParser.pointsWithAltitudeToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testInvalidStringInPointsWithAltitudeToTrack() {
    String line = "42.244814-71.463566";
    Track trkResult = GeographicPositionParser.pointsWithAltitudeToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testEmptyInPointsWithAltitudeToTrack() {
    String line = "";
    Track trkResult = GeographicPositionParser.pointsWithAltitudeToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testPointsToTrack() {
    String line = "42.244814, -71.463566\n42.240618, -71.464372";
    Track trkResult = GeographicPositionParser.pointsToTrack(line);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testMixedPointsInPointsToTrack() {
    String line = "42.244814, -71.463566, 1.6546\n42.240618, -71.464372\n43.240618, -69.664352";
    Track trkResult = GeographicPositionParser.pointsToTrack(line);

    assertEquals("There must be three points in the track", 3, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
    assertTrue("The third point must have an altitude of 0.0", 0.0 == trkResult.getPoint(2).getAltitude());
  }



  @Test
  public void testNoPointsInPointsToTrack() {
    String line = "42.244814\n-71.463566";
    Track trkResult = GeographicPositionParser.pointsToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testInvalidStringInPointsToTrack() {
    String line = "42.244814-71.463566";
    Track trkResult = GeographicPositionParser.pointsToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testEmptyInPointsToTrack() {
    String line = "";
    Track trkResult = GeographicPositionParser.pointsToTrack(line);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }
}
