package de.fhb.polyencoder.parser;

import static org.junit.Assert.*;

import org.junit.*;

import de.fhb.polyencoder.Track;

public class KmlParserTest {
  private StringToTrackParser parser;



  @Before
  public void initParser() {
    parser = ParserFactory.buildKmlParser();
  }



  @Test
  public void testToTrack() {
    String line = "42.244814,-71.463566,0.0 42.240618,-71.464372,1.463566";
    parser.parse(line);
    
    Track trkResult = parser.getTracks().get(0);

    assertEquals("There must be two points in the track", 2, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 0.0", 0.0 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 1.463566", 1.463566 == trkResult.getPoint(1).getAltitude());
  }



  @Test
  public void testParseKmlLineMixedPointsToTrack() {
    String line = "42.244814,-71.463566,1.6546 42.240618,-71.464372 43.240618,-69.664352";
    parser.parse(line);
    
    Track trkResult = parser.getTracks().get(0);

    assertEquals("There must be three points in the track", 3, trkResult.getPoints().size());
    assertTrue("The first point must have an altitude of 1.6546", 1.6546 == trkResult.getPoint(0).getAltitude());
    assertTrue("The second point must have an altitude of 0.0", 0.0 == trkResult.getPoint(1).getAltitude());
    assertTrue("The third point must have an altitude of 0.0", 0.0 == trkResult.getPoint(2).getAltitude());
  }



  @Test
  public void testNoPointsInKmlLineToTrack() {
    String line = "42.244814 -71.463566";
    parser.parse(line);
    
    Track trkResult = parser.getTracks().get(0);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testInvalidKmlLineToTrack() {
    String line = "42.244814-71.463566";
    parser.parse(line);
    
    Track trkResult = parser.getTracks().get(0);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }



  @Test
  public void testEmptyKmlLineToTrack() {
    String line = "";
    parser.parse(line);
    
    Track trkResult = parser.getTracks().get(0);

    assertEquals("The track must have no points", 0, trkResult.getPoints().size());
  }
}
