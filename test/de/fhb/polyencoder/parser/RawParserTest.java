package de.fhb.polyencoder.parser;

import static org.junit.Assert.*;

import org.junit.*;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;

public class RawParserTest {
  private StringToTrackParser parser;



  @Before
  public void initParser() {
    parser = ParserFactory.buildRawParser();
  }



  @Test
  public void testToTrack() {
    String data = Util.readFile("testfiles/raw/singleTrack.raw");
    parser.parse(data);

    assertEquals("There must be one track", 1, parser.getTracks().size());

    Track trk = parser.getTracks().get(0);
    int pointCount = trk.getPoints().size();

    assertTrue("The first point must have an latitude of 51.50018496481678", 51.50018496481678 == trk.getPoint(0).getLatitude());
    assertTrue("The first point must have an longitude of -0.1272211506475873", -0.1272211506475873 == trk.getPoint(0).getLongitude());

    assertTrue("The last point must have an latitude of 51.50013362447846", 51.50013362447846 == trk.getPoint(pointCount - 1).getLatitude());
    assertTrue("The last point must have an longitude of -0.1264294905337338", -0.1264294905337338 == trk.getPoint(pointCount - 1).getLongitude());
  }



  @Test
  public void testMultipleTracks() {
    String data = Util.readFile("testfiles/raw/multipleTracks.raw");
    parser.parse(data);

    assertEquals("There must be two tracks", 2, parser.getTracks().size());

    assertEquals("The first track must have 4 points", 4, parser.getTracks().get(0).getPoints().size());
    assertEquals("The second track must have 3 points", 3, parser.getTracks().get(1).getPoints().size());
  }



  @Test
  public void testCorruptedFile() {
    String data = Util.readFile("testfiles/raw/corrupted.raw");
    parser.parse(data);

    assertEquals("There must be one track", 1, parser.getTracks().size());
    assertEquals("There must be parsed the first 9 points", 9, parser.getTracks().get(0).getPoints().size());
  }



  @Test
  public void testEmptyTrack() {
    String data = Util.readFile("testfiles/raw/emptyTrack.raw");
    parser.parse(data);

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testInvalidPoint() {
    String data = Util.readFile("testfiles/raw/invalidPoint.raw");
    parser.parse(data);

    assertEquals("There must be one track parsed", 1, parser.getTracks().size());
    assertEquals("This track must have 8 points", 8, parser.getTracks().get(0).getPoints().size());
  }



  @Test
  public void testExtraLineEnding() {
    String data = Util.readFile("testfiles/raw/invalidExtraLineEnding.raw");
    parser.parse(data);
    
    assertEquals("There must be one track parsed", 1, parser.getTracks().size());
    assertEquals("This track must have 8 points", 8, parser.getTracks().get(0).getPoints().size());
  }



  @Test
  public void testInvalidPointSeparatorFormat() {
    String data = Util.readFile("testfiles/raw/invalidPointSeparatorFormat.raw");
    parser.parse(data);

    assertEquals("There must be no track parsed", 0, parser.getTracks().size());
  }



  @Test
  public void testInvalidCoordinateSeparatorFormat() {
    String data = Util.readFile("testfiles/raw/invalidCoordinateSeparatorFormat.raw");
    parser.parse(data);

    assertEquals("There must be no track parsed", 0, parser.getTracks().size());
  }



  @Test
  public void testInvertedSeparatorFormat() {
    String data = Util.readFile("testfiles/raw/invertedSeparatorFormat.raw");
    parser.parse(data);

    assertEquals("There must be one track parsed", 1, parser.getTracks().size());
    assertEquals("In the inverted track the parser must find 7 points", 7, parser.getTracks().get(0).getPoints().size());
  }
}