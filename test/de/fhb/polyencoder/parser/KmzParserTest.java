package de.fhb.polyencoder.parser;

import static org.junit.Assert.*;

import org.junit.*;

import de.fhb.polyencoder.Track;

public class KmzParserTest {
  private StringToTrackParser parser;



  @Before
  public void initParser() {
    parser = ParserFactory.buildKmzParser();
  }



  @Test
  public void testSingleTrack() {
    parser.parseFile("testfiles/kmz/singleTrack.kmz");

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
    parser.parseFile("testfiles/kmz/multipleTracks.kmz");

    assertEquals("There must be two tracks", 2, parser.getTracks().size());
    
    assertEquals("The first track must have 17 points", 17, parser.getTracks().get(0).getPoints().size());
    assertEquals("The second track must have 6 points", 6, parser.getTracks().get(1).getPoints().size());
  }



  @Test
  public void testCorruptedFileBeginning() {
    parser.parseFile("testfiles/kmz/corruptedBeginning.kmz");

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testCorruptedFileMiddle() {
    parser.parseFile("testfiles/kmz/corruptedMiddle.kmz");

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testCorruptedFileEnd() {
    parser.parseFile("testfiles/kmz/corruptedEnd.kmz");

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testEmptyFile() {
    parser.parseFile("testfiles/kmz/empty.kmz");

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testWrongFile() {
    parser.parseFile("testfiles/kmz/noKMZ.kmz");

    assertEquals("There must be no track parsed", 0, parser.getTracks().size());
  }
  


  @Test
  public void testNoMainDocInFile() {
    parser.parseFile("testfiles/kmz/noMainDoc.kmz");

    assertEquals("There must be no track parsed", 0, parser.getTracks().size());
  }



  @Test
  public void testMoreFilesAndMainDoc() {
    parser.parseFile("testfiles/kmz/moreFilesAndMainDoc.kmz");

    assertEquals("There must be two track parsed", 2, parser.getTracks().size());
  }



  @Test
  public void testMoreFilesWithoutMainDoc() {
    parser.parseFile("testfiles/kmz/moreFilesWithoutMainDoc.kmz");

    assertEquals("There must be one track parsed", 0, parser.getTracks().size());
  }



  @Test
  public void testMultipleKmlInKmz() {
    parser.parseFile("testfiles/kmz/multipleKML.kmz");

    assertEquals("There must only one track parsed.", 1, parser.getTracks().size());
    assertTrue("This track must be from doc.kml Therefore the first point must have an latitude of 51.50016313585792.", 51.50016313585792 == parser.getTracks().get(0).getPoint(0).getLatitude());
  }
}