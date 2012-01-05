package de.fhb.polyencoder.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;

public class GpxParserTest {
  private StringToTrackParser parser;



  @Before
  public void initParser() {
    parser = ParserFactory.buildGpxParser();
  }



  @Test
  public void testCorruptedFile() {
    String data = Util.readFile("testfiles/gpx/corrupted.gpx");
    parser.parse(data);

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testEmptyTrack() {
    String data = Util.readFile("testfiles/gpx/emptyTrack.gpx");
    parser.parse(data);

    assertEquals("There must be no track", 0, parser.getTracks().size());
  }



  @Test
  public void testInvalidPoint() {
    String data = Util.readFile("testfiles/gpx/invalidPoint.gpx");
    parser.parse(data);

    assertEquals("There must be one track parsed", 1, parser.getTracks().size());
    assertEquals("This track must have one point", 1, parser.getTracks().get(0).getPoints().size());
  }



  @Test
  public void testSingleRouteGPX1_1() {
    String data = Util.readFile("testfiles/gpx/routepoints_GPX_1.1.gpx");
    parser.parse(data);

    assertEquals("There must be one track inside the GPX-File", 1, parser.getTracks().size());
    assertEquals("The track must have 48 points", 48, parser.getTracks().get(0).getPoints().size());
  }



  @Test
  public void testMultipleTracksMixedWithWaypointsGPX1_0() {
    String data = Util.readFile("testfiles/gpx/waypoints_trackpoints_GPX_1.0.gpx");
    parser.parse(data);

    List<Track> tracks = parser.getTracks();

    int trackCount = tracks.size();

    assertEquals("There must be 12 tracks inside the GPX-File", 12, trackCount);

    int trackPointCount = 0;
    for (int i = 0; i < trackCount - 1; i++) {
      trackPointCount += tracks.get(i).getPoints().size();
    }
    assertEquals("All tracks (excluding waypoints!) must have a sum of 348 trackpoints", 348, trackPointCount);
  }



  @Test
  public void testWaypointsAndTrackpointsGPX1_0() {
    String data = Util.readFile("testfiles/gpx/waypoints_trackpoints_GPX_1.0.gpx");
    parser.parse(data);

    List<Track> tracks = parser.getTracks();

    int trackCount = tracks.size();

    Track trkTrack = parser.getTracks().get(0);
    int trkTrackSize = trkTrack.getPoints().size();

    Track trkWaypoints = parser.getTracks().get(trackCount - 1);
    int trkWaypointsSize = trkWaypoints.getPoints().size();

    assertEquals("The first track must have 18 points.", 18, trkTrackSize);

    assertEquals("There must be 25 Waypoints in the GPX-File", 25, trkWaypointsSize);

    assertTrue("The first waypoint must have an altitude of 0.0", 0.0 == trkWaypoints.getPoint(0).getAltitude());
    assertTrue("The last waypoint must have an altitude of 128.016", 128.016 == trkWaypoints.getPoint(trkWaypointsSize - 1).getAltitude());
  }
}
