package de.fhb.polyencoder.parser;

import static org.junit.Assert.*;

import org.junit.*;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;

public class KmlParserTest {
  private StringToTrackParser parser;



  @Before
  public void initParser() {
    parser = ParserFactory.buildKmlParser();
  }



  @Test
  public void testToTrack() {
    String data = Util.readFile("testfiles/singleTrack.kml");
    parser.parse(data);

    assertEquals("There must be one track", 1, parser.getTracks().size());
    
    Track trk = parser.getTracks().get(0);
    int pointCount = trk.getPoints().size();
    
    assertTrue("The first point must have an latitude of 51.4997138231825", 51.4997138231825 == trk.getPoint(0).getLatitude());
    assertTrue("The first point must have an longitude of -0.1272722324704489", -0.1272722324704489 == trk.getPoint(0).getLongitude());
    
    assertTrue("The last point must have an latitude of 51.50317646812461", 51.50317646812461 == trk.getPoint(pointCount - 1).getLatitude());
    assertTrue("The last point must have an longitude of -0.11950475968451", -0.11950475968451 == trk.getPoint(pointCount - 1).getLongitude());
  }



  @Test
  public void testMultipleTracks() {
    String data = Util.readFile("testfiles/multipleTracks.kml");
    parser.parse(data);

    assertEquals("There must be two tracks", 2, parser.getTracks().size());
    
    assertEquals("The first track must have 17 points", 17, parser.getTracks().get(0).getPoints().size());
    assertEquals("The second track must have 6 points", 6, parser.getTracks().get(1).getPoints().size());
  }



  @Test
  public void testCorruptedKMLFile() {
    String data = Util.readFile("testfiles/corrupted.kml");
    parser.parse(data);

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testEmptyTrack() {
    String data = Util.readFile("testfiles/emptyTrack.kml");
    parser.parse(data);

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testInvalidPointKMLFile() {
    String data = Util.readFile("testfiles/invalidPoint.kml");
    parser.parse(data);

    assertEquals("There must be one track parsed", 1, parser.getTracks().size());
    assertEquals("This track must have one point", 16, parser.getTracks().get(0).getPoints().size());
  }
}