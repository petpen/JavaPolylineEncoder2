package de.fhb.polyencoder;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author Peter Pensold
 */
public class PolylineEncoderTest {
  private static PolylineEncoder encoder;



  @Test
  public void testEncodeFalseBackslash() {
    String in = "\n";
    String out = "\n";
    assertEquals("A backslash which acts as an control character should not be encoded.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeOneBackslashOnly() {
    String in = "\\";
    String out = "\\\\";
    assertEquals("One backslash must be encoded to two backslashes.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeOneBackslashOnBeginning() {
    String in = "\\Test";
    String out = "\\\\Test";
    assertEquals("One backslash must be encoded to two backslashes.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeOneBackslashOnEnd() {
    String in = "Test\\";
    String out = "Test\\\\";
    assertEquals("One backslash must be encoded to two backslashes.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeOneBackslashMiddle() {
    String in = "Te\\st";
    String out = "Te\\\\st";
    assertEquals("One backslash must be encoded to two backslashes.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeTwoBackslashes() {
    String in = "\\\\";
    String out = "\\\\\\\\";
    assertEquals("Should be four backslashes after encoding.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeTwoBackslashesOnBeginning() {
    String in = "\\\\Test";
    String out = "\\\\\\\\Test";
    assertEquals("Should be four backslashes after encoding.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeTwoBackslashesOnEnd() {
    String in = "Test\\\\";
    String out = "Test\\\\\\\\";
    assertEquals("Should be four backslashes after encoding.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeTwoBackslashesMiddle() {
    String in = "Te\\\\st";
    String out = "Te\\\\\\\\st";
    assertEquals("Should be four backslashes after encoding.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeTwoBackslashesDiverted() {
    String in = "Te\\st\\";
    String out = "Te\\\\st\\\\";
    assertEquals("Should be four backslashes after encoding.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testEncodeBackslashesUneven() {
    String in = "Te\\\nst";
    String out = "Te\\\\\nst";
    assertEquals("Should be two backslashes and a linewrap after encoding.", out, PolylineEncoder.encodeBackslash(in));
  }



  @Test
  public void testParseEmptyStringToTrack() {
    String pointIn = "";
    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = PolylineEncoder.parseStringToTrack(pointIn, sep, pos);

    assertTrue("There should be no points in the Track", 0 == trkResult.getPoints().size());
  }



  @Test
  public void testParseStringToTrackLatLng() {
    String pointIn = "42.244814 -71.463566";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = PolylineEncoder.parseStringToTrack(pointIn, sep, pos);

    assertTrue("There should only be one point in the Track", 1 == trkResult.getPoints().size());
    assertTrue("Latitude should be 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("Longitude should be -71.463566", -71.463566 == trkResult.getPoint(0).getLongitude());
    assertTrue("Altitude should be 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
  }



  @Test
  public void testParseStringToTrackLatLngAlt() {
    String pointIn = "42.244814 -71.463566 1.463566";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1, 2);
    Track trkResult = PolylineEncoder.parseStringToTrack(pointIn, sep, pos);

    assertTrue("There should only be one point in the Track", 1 == trkResult.getPoints().size());
    assertTrue("Latitude should be 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("Longitude should be -71.463566", -71.463566 == trkResult.getPoint(0).getLongitude());
    assertTrue("Altitude should be 1.463566", 1.463566 == trkResult.getPoint(0).getAltitude());
  }



  @Test
  public void testParseStringWithMultiplePointsToTrackLatLng() {
    String pointsIn = "42.244814 -71.463566\n42.240618 -71.464372";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
    Track trkResult = PolylineEncoder.parseStringToTrack(pointsIn, sep, pos);
    
    assertTrue("There should only be two points in the Track", 2 == trkResult.getPoints().size());
    assertTrue("There first point must have an latitude of 42.244814", 42.244814 == trkResult.getPoint(0).getLatitude());
    assertTrue("There first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("There second point must have an latitude of 42.240618", 42.240618 == trkResult.getPoint(1).getLatitude());
    assertTrue("There second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testParseStringWithMultiplePointsToTrackLatLngAlt() {
    String pointsIn = "42.244814 -71.463566 1.463566\n42.240618 -71.464372 0.0";

    TrackSeparator sep = new TrackSeparator("\n", " ");
    PointArrayPositions pos = new PointArrayPositions(0, 1, 2);
    Track trkResult = PolylineEncoder.parseStringToTrack(pointsIn, sep, pos);
    
    assertTrue("There should only be two points in the Track", 2 == trkResult.getPoints().size());
    assertTrue("There first point must have an altitude of 1.463566", 1.463566 == trkResult.getPoint(0).getAltitude());
    assertTrue("There second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
  }



  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    encoder = new PolylineEncoder();
  }
}
